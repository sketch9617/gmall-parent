package com.atguigu.gmall.cart.api;

import com.atguigu.gmall.cart.service.CartService;
import com.atguigu.gmall.common.auth.AuthUtils;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.model.vo.user.UserAuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author sketch
 * @date 2022/9/8 20:37
 * @description
 */
@RestController
@RequestMapping("/api/inner/rpc/cart")
@Slf4j
public class CartApiController {

    @Autowired
    CartService cartService;

    /**
     * 1.商品添加到购物车
     */
    @GetMapping("/addToCart")
    public Result<SkuInfo> addToCart(@RequestParam("skuId") Long skuId,
                                     @RequestParam("num") Integer num){

        UserAuthInfo authInfo = AuthUtils.getCurrentAuthInfo();
        log.info("用户id:{}, 临时id:{}", authInfo.getUserId(), authInfo.getUserTempId());
        SkuInfo skuInfo = cartService.addToCart(skuId,num);
        return Result.ok(skuInfo);
    }

    /**
     * 2.删除购物车中选中的商品
     */
    @GetMapping("/deleteChecked")
    public Result deleteChecked(){
        String cartKey = cartService.determinCartKey();
        cartService.deleteChecked(cartKey);
        return Result.ok();
    }
}
