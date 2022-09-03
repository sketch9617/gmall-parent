package com.atguigu.gmall.item.service.impl;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.util.Jsons;
import com.atguigu.gmall.feign.product.SkuProductFeignClient;
import com.atguigu.gmall.item.service.SkuDetailService;
import com.atguigu.gmall.model.product.SkuImage;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.product.SpuSaleAttr;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.starter.cache.annotation.GmallCache;
import com.atguigu.starter.cache.service.CacheOpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sketch
 * @date 2022/8/26 22:03
 * @description 商品详情多线程异步编排
 */
@Service
@Slf4j
public class SkuDetailServiceImpl implements SkuDetailService {
    @Autowired
    SkuProductFeignClient skuDetailFeignClient;
    @Autowired
    ThreadPoolExecutor executor;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    CacheOpsService cacheOpsService;

    Map<Long, ReentrantLock> lockPool = new ConcurrentHashMap<>();
    ReentrantLock lock = new ReentrantLock();



    //未使用缓存
    public SkuDetailTo getSkuDetailFromRpc(Long skuId) {
        SkuDetailTo detailTo = new SkuDetailTo();

        //1. 查询sku的基本信息
        CompletableFuture<SkuInfo> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            Result<SkuInfo> result = skuDetailFeignClient.getSkuInfo(skuId);
            SkuInfo skuInfo = result.getData();
            detailTo.setSkuInfo(skuInfo);
            return skuInfo;
        }, executor);

        //2. 查询sku图片信息
        CompletableFuture<Void> imageFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            if (skuInfo != null) {
                Result<List<SkuImage>> skuImages = skuDetailFeignClient.getSkuImages(skuId);
                skuInfo.setSkuImageList(skuImages.getData());
            }
        }, executor);

        //3. 查询sku完整分类信息
        CompletableFuture<Void> categoryFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            if (skuInfo != null) {
                Result<CategoryViewTo> categoryView = skuDetailFeignClient.getCategoryView(skuInfo.getCategory3Id());
                detailTo.setCategoryView(categoryView.getData());
            }
        }, executor);

        //4. 查询sku实时价格
        CompletableFuture<Void> priceFuture = CompletableFuture.runAsync(() -> {
            Result<BigDecimal> price = skuDetailFeignClient.getSku1010Price(skuId);
            detailTo.setPrice(price.getData());
        }, executor);

        //5. 查询spu下的所有sku销售属性组合并排序
        CompletableFuture<Void> saleAttrFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            if (skuInfo != null) {
                Long spuId = skuInfo.getSpuId();
                Result<List<SpuSaleAttr>> saleattrvalues = skuDetailFeignClient.getSkuSaleattrvalues(skuId, spuId);
                detailTo.setSpuSaleAttrList(saleattrvalues.getData());
            }
        }, executor);

        //6. 查sku同级别所有销售属性组合,封装成json
        CompletableFuture<Void> skuVlaueFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            if (skuInfo != null) {
                Result<String> sKuValueJson = skuDetailFeignClient.getSKuValueJson(skuInfo.getSpuId());
                detailTo.setValuesSkuJson(sKuValueJson.getData());
            }
        }, executor);

        CompletableFuture
                .allOf(imageFuture,priceFuture,saleAttrFuture,skuVlaueFuture,categoryFuture)
                .join();

        return detailTo;
    }

    //1.redis本地缓存+本地锁
    public SkuDetailTo getSkuDetail2(Long skuId) {
        //1、看缓存中有没有  sku:info:50
        String jsonStr = redisTemplate.opsForValue().get("sku:info:" + skuId);
        if("x".equals(jsonStr)){
            //说明以前查过，只不过数据库没有此记录，为了避免再次回源，缓存了一个占位符
            return null;
        }
        //
        if(StringUtils.isEmpty(jsonStr)){
            //2、redis没有缓存数据
            //2.1、回源。之前可以判断redis中保存的sku的id集合，有没有这个id
            //防止随机值穿透攻击？
            SkuDetailTo fromRpc = getSkuDetailFromRpc(skuId);
            //2.2、放入缓存【查到的对象转为json字符串保存到redis】
            String cacheJson = "x";
            if(fromRpc!=null){
                cacheJson =  Jsons.toStr(fromRpc);
                redisTemplate.opsForValue().set("sku:info:" + skuId,cacheJson,7, TimeUnit.DAYS);
            }else {
                redisTemplate.opsForValue().set("sku:info:" + skuId,cacheJson,30,TimeUnit.MINUTES);
            }

            return fromRpc;
        }
        //3、缓存中有. 把json转成指定的对象
        SkuDetailTo skuDetailTo = Jsons.toObj(jsonStr,SkuDetailTo.class);
        return skuDetailTo;
    }

    //2.redis分布式缓存+布隆过滤器
    public SkuDetailTo getSkuDetailWithCache(Long skuId) {
        String cacheKey = SysRedisConst.SKU_INFO_PREFIX +skuId;
        //1、先查缓存
        SkuDetailTo cacheData = cacheOpsService.getCacheData(cacheKey,SkuDetailTo.class);
        //2、判断
        if(cacheData == null){
            //3、缓存没有
            //4、先问布隆，是否有这个商品
            boolean contain = cacheOpsService.bloomContains(skuId);
            if(!contain){
                //5、布隆说没有，一定没有
                log.info("[{}]商品 - 布隆判定没有，检测到隐藏的攻击风险....",skuId);
                return null;
            }
            //6、布隆有，可能有，就需要回源查数据
            boolean lock = cacheOpsService.tryLock(skuId); //为当前商品加自己的分布式锁。100w的49号查询只会放进一个
            if(lock){
                //7、获取锁成功，查询远程
                log.info("[{}]商品 缓存未命中，布隆说有，准备回源.....",skuId);
                SkuDetailTo fromRpc = getSkuDetailFromRpc(skuId);
                //8、数据放缓存
                cacheOpsService.saveData(cacheKey,fromRpc);
                //9、解锁
                cacheOpsService.unlock(skuId);
                return fromRpc;
            }
            //9、没获取到锁
            try {Thread.sleep(1000);
                return cacheOpsService.getCacheData(cacheKey,SkuDetailTo.class);
            } catch (InterruptedException e) {

            }
        }
        //4、缓存中有
        return cacheData;
    }

    @GmallCache(
            cacheKey =SysRedisConst.SKU_INFO_PREFIX+"#{#params[0]}",
            bloomName = SysRedisConst.BLOOM_SKUID,
            bloomValue = "#{#params[0]}",
            lockName = SysRedisConst.LOCK_SKU_DETAIL+"#{#params[0]}",
            ttl = 60*60*24*7L
    )
    @Override
    public SkuDetailTo getSkuDetail(Long skuId) {
        SkuDetailTo fromRpc = getSkuDetailFromRpc(skuId);
        return fromRpc;
    }
}
