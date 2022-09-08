package com.atguigu.gmall.cart.api;

import com.atguigu.gmall.common.constant.SysRedisConst;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sketch
 * @date 2022/9/8 20:37
 * @description
 */
@RestController
@RequestMapping("/api/inner/rpc/cart")
public class CartApiController {
    /**
     * 1.商品添加到购物车
     */
    public Result<SkuInfo> addToCart(@RequestParam("skuId") Long skuId,
                                     @RequestParam("num") Integer num,
                                     @RequestHeader(value = SysRedisConst.USERID_HEADER, required = false) String userId) {
        // TODO: 2022/9/8  
        return Result.ok();
    }
}
