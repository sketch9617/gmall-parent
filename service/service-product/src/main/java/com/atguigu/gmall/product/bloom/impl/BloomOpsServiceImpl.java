package com.atguigu.gmall.product.bloom.impl;

import com.atguigu.gmall.product.bloom.BloomDataQueryService;
import com.atguigu.gmall.product.bloom.BloomOpsService;
import com.atguigu.gmall.product.service.SkuInfoService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sketch
 * @date 2022/9/1 21:06
 * @description
 */
@Service
public class BloomOpsServiceImpl implements BloomOpsService {

    @Autowired
    RedissonClient redissonClient;
    @Autowired
    SkuInfoService skuInfoService;

    @Override
    public void rebuildBloom(String bloomName, BloomDataQueryService dataQueryService) {
        RBloomFilter<Object> oldbloomFilter = redissonClient.getBloomFilter(bloomName);
        //1.新建布隆过滤器
        String newBloomName = bloomName + "_new";
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(newBloomName);

        //2.获取所有sku商品id
        List list = dataQueryService.queryData();

        //3.初始化新布隆过滤器
        bloomFilter.tryInit(5000000, 0.00001);
        for (Object skuId : list) {
            bloomFilter.add(skuId);
        }

        //4.新旧交换,nb要变成ob.大数据量的删除会导致redis卡死. 推荐做法：lua。 自己尝试写一下这lua脚本
        oldbloomFilter.rename("dd_bloom");//老布隆下线
        bloomFilter.rename(bloomName);//新布隆上线

        //5.删除oldBloom,中间层交换
        oldbloomFilter.deleteAsync();
        redissonClient.getBloomFilter("dd_bloom").deleteAsync();
    }
}
