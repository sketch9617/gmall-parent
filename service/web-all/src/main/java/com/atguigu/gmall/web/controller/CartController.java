package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.feign.cart.CartFeignClient;
import com.atguigu.gmall.model.product.SkuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sketch
 * @date 2022/9/8 20:43
 * @description
 */
@Controller
public class CartController {
    @Autowired
    CartFeignClient cartFeignClient;

    /**
     * 商品添加到购物车
     */
    @GetMapping("/addCart.html")
    public String addCarthtml(@RequestParam("skuId") Long skuId,
                              @RequestParam("skuNum") Integer skuNum){
        //把指定商品添加到购物车
//        System.out.println("web-all 获取到的用户id：");
//        Result<SkuInfo> result = cartFeignClient.addToCart(skuId, skuNum);
//        model.addAttribute("skuInfo",result.getData());
//        model.addAttribute("skuNum",skuNum);

        return "cart/addCart";
    }
}
