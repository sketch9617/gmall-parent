package com.atguigu.gmall.item.service;

import com.atguigu.gmall.model.to.SkuDetailTo;

/**
 * @author sketch
 * @date 2022/8/26 22:02
 * @description
 */
public interface SkuDetailService {
    SkuDetailTo getSkuDetail(Long skuId);
}
