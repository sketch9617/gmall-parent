package com.atguigu.gmall.item.cache;

/**
 * @author sketch
 * @date 2022/8/31
 * @description
 */
public interface CacheOpsService {

    <T>T getCacheData(String cacheKey, Class<T> clz);

    /**
     * 1.布隆过滤器判断是否有这个商品
     * @param skuId
     * @return
     */
    boolean bloomContains(Long skuId);

    /**
     * 2.给指定商品加锁
     * @param skuId
     * @return
     */
    boolean tryLock(Long skuId);

    /**
     * 3.把指定对象使用指定的key保存到redis
     * @param cacheKey
     * @param fromRpc
     */
    void saveData(String cacheKey, Object fromRpc);

    /**
     * 4.解锁
     * @param skuId
     */
    void unlock(Long skuId);
}
