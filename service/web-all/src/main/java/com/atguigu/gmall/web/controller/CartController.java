package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.feign.cart.CartFeignClient;
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
     * 添加商品到购物车
     */
    @GetMapping("/addCart.html")
    public String addCarthtml(@RequestParam("skuId") Long skuId,
                              @RequestParam("skuNum") Integer skuNum,
                              Model model){
        //SpringMVC每次收到请求以后，这个请求默认就和线程绑定好了
        //1、把指定商品添加到购物车
        System.out.println("web-all 获取到的用户id：");
        Result<Object> result = cartFeignClient.addToCart(skuId, skuNum);
        if (result.isOk()) {
            model.addAttribute("skuInfo",result.getData());
            model.addAttribute("skuNum",skuNum);
            return "cart/addCart";
        }else {
            String message = result.getMessage();
            model.addAttribute("msg",result.getData());
            return "cart/error";
        }

    }

    /**
     * 购物车列表页
     */
    @GetMapping("/cart.html")
    public String cartHtml(){
        return "cart/index";
    }

    /**
     * 删除购物车中选中商品
     */
    @GetMapping("/cart/deleteChecked")
    public String deleteChecked(){

        /**
         * redirect: 重定向
         * forward: 转发
         */
        cartFeignClient.deleteChecked();
        return "redirect:http://cart.gmall.com/cart.html";
    }
}
