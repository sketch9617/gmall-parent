package com.atguigu.gmall.search.service;

import com.atguigu.gmall.model.list.Goods;
import com.atguigu.gmall.model.vo.SearchParamVo;
import com.atguigu.gmall.model.vo.SearchResponseVo;

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

    /**
     * 3.商品搜索
     */
    SearchResponseVo search(SearchParamVo paramVo);

    /**
     * 4.更新热度分数
     */
    void updateHotScore(Long skuId, Long score);
}
