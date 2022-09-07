package com.atguigu.gmall.item.service;

import com.atguigu.gmall.model.to.SkuDetailTo;

/**
 * @author sketch
 * @date 2022/8/26 22:02
 * @description
 */
public interface SkuDetailService {
    /**
     * 查询商品详情
     */
    SkuDetailTo getSkuDetail(Long skuId);

    /**
     * 更新热度分数,100一次
     */
    void updateHotScore(Long skuId);
}
