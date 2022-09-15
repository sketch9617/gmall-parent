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
    public static final int SEARCH_PAGE_SIZE = 10;
    public static final String SKU_HOTSCORE_PREFIX = "sku:hotscore:";
    public static final String LOGIN_USER = "user:login:";
    public static final String USERID_HEADER = "userid";
    public static final String USERTEMPID_HEADER = "usertempid";
    public static final String CART_KEY = "cart:user:"; //用户id或临时id
    public static final long CART_ITEMS_LIMIT = 200;//购物车中商品条目总数限制
    public static final Integer CART_ITEM_NUM_LIMIT = 200;//单个商品数量限制
    public static final String ORDER_TEMP_TOKEN = "order:temptoken:";//订单防重令牌,只需要保存15min
    //订单超时关闭时间
    public static final Integer ORDER_CLOSE_TTL = 60*45; //秒为单位
    public static final Integer ORDER_REFUND_TTL = 60*60*24*30;
}