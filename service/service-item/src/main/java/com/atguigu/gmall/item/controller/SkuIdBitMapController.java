package com.atguigu.gmall.item.controller;

import com.atguigu.gmall.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sketch
 * @date 2022/9/1 19:26
 * @description
 */
@RestController
public class SkuIdBitMapController {
    /**
     * 同步数据库中所有商品id占位标识
     */
    @GetMapping("/sync/skuid/bitmap")
    public Result syncBitMap() {
        //查询数据库中所有商品id, 进行置位
        return Result.ok();
    }
}
