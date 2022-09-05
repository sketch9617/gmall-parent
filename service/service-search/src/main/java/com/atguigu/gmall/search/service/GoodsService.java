package com.atguigu.gmall.search.service;

import com.atguigu.gmall.model.list.Goods;

/**
 * @author sketch
 * @date 2022/9/5 14:49
 * @description
 */
public interface GoodsService {
    /**
     * 1.上架商品
     */
    void saveGoods(Goods goods);

    /**
     * 2.下架商品
     */
    void deleteGoods(Long skuId);
}
