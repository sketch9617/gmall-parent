package com.atguigu.gmall.cart.controller;

import com.atguigu.gmall.cart.service.CartService;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.cart.CartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sketch
 * @date 2022/9/8 20:37
 * @description 购物车处理前端 ajax请求
 */
@RequestMapping("/api/cart")
@RestController
public class CartRestController {

    @Autowired
    CartService cartService;

    /**
     * 购物车列表
     */
    @GetMapping("/cartList")
    public Result cartList(){
        //1、决定用哪个购物车键
        String cartKey = cartService.determinCartKey();
        //先尝试合并购物车
        cartService.mergeUserAndTempCart();
        //2、获取这个购物车中所有商品
        List<CartInfo> infos = cartService.getCartList(cartKey);
        return Result.ok(infos);
    }


    /**
     * 修改购物车中某个商品数量
     * @param skuId
     * @param num  1和-1
     */
    @PostMapping("/addToCart/{skuId}/{num}")
    public Result updateItemNum(@PathVariable("skuId") Long skuId,
                                @PathVariable("num") Integer num){

        String cartKey = cartService.determinCartKey();
        cartService.updateItemNum(skuId,num,cartKey);
        return Result.ok();
    }


    /**
     * 修改勾选状态
     */
    @GetMapping("/checkCart/{skuId}/{status}")
    public Result check(@PathVariable("skuId") Long skuId,
                        @PathVariable("status") Integer status){

        String cartKey = cartService.determinCartKey();
        cartService.updateChecked(skuId,status,cartKey);

        return Result.ok();
    }


    /**
     * 删除购物车中商品
     */
    @DeleteMapping("/deleteCart/{skuId}")
    public Result deleteItem(@PathVariable("skuId") Long skuId){

        String cartKey = cartService.determinCartKey();
        cartService.deleteCartItem(skuId,cartKey);

        return Result.ok();
    }
}
