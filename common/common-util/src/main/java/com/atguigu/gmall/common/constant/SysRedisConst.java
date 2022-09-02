package com.atguigu.gmall.common.constant;

/**
 * @author sketch
 * @date 2022/8/31
 * @description redis缓存常量
 */
public class SysRedisConst {
    public static final String NULL_VAL = "x";
    public static final String LOCK_SKU_DETAIL = "lock:sku:detail:";
    public static final Long NULL_VAL_TTL = 60*30L;
    public static final Long SKUDETAIL_TTL = 60*60*24*7L;
    public static final String SKU_INFO_PREFIX = "sku:info:";
    public static final String BLOOM_SKUID = "bloom:skuid";
    public static final String CACHE_CATEGORYS = "categorys";
    public static final String LOCK_PREFIX = "lock:";
}