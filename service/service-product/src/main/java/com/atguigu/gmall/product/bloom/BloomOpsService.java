package com.atguigu.gmall.product.bloom;

/**
 * @author sketch
 * @date 2022/9/1 21:03
 * @description
 */
public interface BloomOpsService {
    /**
     * 重建指定布隆过滤器
     */
    void rebuildBloom(String bloomName, BloomDataQueryService dataQueryService);
}
