package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.feign.search.SearchFeignClient;
import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.list.SearchAttr;
import com.atguigu.gmall.model.product.*;
import com.atguigu.gmall.model.to.CategoryViewTo;
import com.atguigu.gmall.model.to.SkuDetailTo;
import com.atguigu.gmall.product.mapper.BaseCategory3Mapper;
import com.atguigu.gmall.product.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.mapper.SkuInfoMapper;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @author TANGLED
* @description 针对表【sku_info(库存单元表)】的数据库操作Service实现
* @createDate 2022-08-23 21:40:40
*/
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo>
    implements SkuInfoService{

    @Resource
    SkuInfoMapper skuInfoMapper;
    @Autowired
    SkuImageService skuImageService;
    @Autowired
    SkuAttrValueService skuAttrValueService;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Resource
    BaseCategory3Mapper baseCategory3Mapper;
    @Autowired
    SpuSaleAttrService spuSaleAttrService;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    BaseTrademarkService baseTrademarkService;
    @Autowired
    SearchFeignClient searchFeignClient;

    @Override
    @Transactional
    public void saveSkuInfo(SkuInfo info) {
        //1、sku基本信息保存到 sku_info
        save(info);
        Long skuId = info.getId();

        //2、sku的图片信息保存到 sku_image
        for (SkuImage skuImage : info.getSkuImageList()) {
            skuImage.setSkuId(skuId);
        }
        skuImageService.saveBatch(info.getSkuImageList());

        //3、sku的平台属性名和值的关系保存到 sku_attr_value
        List<SkuAttrValue> attrValueList = info.getSkuAttrValueList();
        for (SkuAttrValue attrValue : attrValueList) {
            attrValue.setSkuId(skuId);
        }
        skuAttrValueService.saveBatch(attrValueList);

        //4、sku的销售属性名和值的关系保存到 sku_sale_attr_value
        List<SkuSaleAttrValue> saleAttrValueList = info.getSkuSaleAttrValueList();
        for (SkuSaleAttrValue saleAttrValue : saleAttrValueList) {
            saleAttrValue.setSkuId(skuId);
            saleAttrValue.setSpuId(info.getSpuId());
        }
        skuSaleAttrValueService.saveBatch(saleAttrValueList);

        //把SkuId放到布隆过滤器中
        RBloomFilter<Object> filter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        filter.add(skuId);
    }

    @Override
    public void onSale(Long skuId) {
        //1.改数据库sku_info 这个skuId的is_sale； 1上架  0下架
        skuInfoMapper.updateIsSale(skuId,1);
        //2.给es中保存这个商品，商品就能被检索到了
        Goods goods = getGoodsBySkuId(skuId);
        searchFeignClient.saveGoods(goods);
    }

    @Override
    public void cancelSale(Long skuId) {
        skuInfoMapper.updateIsSale(skuId,0);
        //从es中删除
        searchFeignClient.deleteGoods(skuId);
    }

    /*
    //商品详情,已抽取
    @Override
    public SkuDetailTo getSkuDetail(Long skuId) {

        SkuDetailTo detailTo = new SkuDetailTo();
        //1. 查询存在sku信息
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);

        //2. 获取sku基本信息
        detailTo.setSkuInfo(skuInfo);

        //3. 根据skuId获取对应图片
        List<SkuImage> imageList = skuImageService.getSkuImage(skuId);
        skuInfo.setSkuImageList(imageList);

        //4. 获取sku完整分类信息
        CategoryViewTo categoryViewTo = baseCategory3Mapper.getCategoryView(skuInfo.getCategory3Id());
        detailTo.setCategoryView(categoryViewTo);

        //5. 获取sku商品实时价格
        BigDecimal price = skuInfoMapper.getRealPrice(skuId);
        detailTo.setPrice(price);

        //6. 获取spu下的所有sku销售属性组合并排序
        List<SpuSaleAttr> saleAttrList = spuSaleAttrService.getSaleAttrAndValueMarkSku(skuInfo.getSpuId(),skuId);
        detailTo.setSpuSaleAttrList(saleAttrList);

        //7. 获取sku同级别所有销售属性组合,封装成json
        Long spuId = skuInfo.getSpuId();
        String valuejson = spuSaleAttrService.getAllSkuSaleAttrValueJson(spuId);
        detailTo.setValuesSkuJson(valuejson);

        return detailTo;
    }*/

    @Override
    public BigDecimal get1010Price(Long skuId) {
        BigDecimal price = skuInfoMapper.getRealPrice(skuId);
        return price;
    }

    @Override
    public SkuInfo getDetailSkuInfo(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        return skuInfo;
    }

    @Override
    public List<SkuImage> getDetailSkuImages(Long skuId) {
        List<SkuImage> imageList = skuImageService.getSkuImage(skuId);
        return imageList;
    }

    @Override
    public List<Long> findAllSkuId() {
        return skuInfoMapper.getAllSkuId();
    }

    @Override
    public Goods getGoodsBySkuId(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);

        Goods goods = new Goods();
        goods.setId(skuId);
        goods.setDefaultImg(skuInfo.getSkuDefaultImg());
        goods.setTitle(skuInfo.getSkuName());
        goods.setPrice(skuInfo.getPrice().doubleValue());
        goods.setCreateTime(new Date());
        goods.setTmId(skuInfo.getTmId());

        BaseTrademark trademark = baseTrademarkService.getById(skuInfo.getTmId());
        goods.setTmName(trademark.getTmName());
        goods.setTmLogoUrl(trademark.getLogoUrl());

        Long category3Id = skuInfo.getCategory3Id();
        CategoryViewTo view = baseCategory3Mapper.getCategoryView(category3Id);
        goods.setCategory1Id(view.getCategory1Id());
        goods.setCategory1Name(view.getCategory1Name());
        goods.setCategory2Id(view.getCategory2Id());
        goods.setCategory2Name(view.getCategory2Name());
        goods.setCategory3Id(view.getCategory3Id());
        goods.setCategory3Name(view.getCategory3Name());

        goods.setHotScore(0L);
        //查当前sku所有平台属性名和值
        List<SearchAttr> attrs = skuAttrValueService.getSkuAttrNameAndValue(skuId);
        goods.setAttrs(attrs);
        return goods;
    }
}




