package com.atguigu.gmall.feign.cart;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sketch
 * @date 2022/9/8 18:44
 * @description
 */
@RequestMapping("/api/inner/rpc/cart")
@FeignClient("service-cart")
public interface CartFeignClient {
    /**
     * 商品添加至购物车
     */
    @GetMapping("/addToCart")
    Result<SkuInfo> addToCart(@RequestParam("skuId") Long skuId,
                              @RequestParam("num") Integer num);
}