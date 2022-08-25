package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SkuInfo;
import com.atguigu.gmall.product.service.SkuInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author sketch
 * @date 2022/8/25 15:54
 * @description
 */
@RestController
@RequestMapping("/admin/product")
public class SkuController {

    // TODO: 2022/8/25

    @Autowired
    SkuInfoService skuInfoService;

    /**
     * 1. sku分页查询
     */
    @GetMapping("/list/{pn}/{ps}")
    public Result getSkuList(@PathVariable("pn") Long pn,
                             @PathVariable("ps") Long ps){
        Page<SkuInfo> page =new Page<>(pn,ps);
        Page<SkuInfo> result = skuInfoService.page(page);
        return Result.ok(result);
    }

    /**
     * 2. 保存所有sku信息
     */
    @PostMapping("/saveSkuInfo")
    public Result saveSku(@RequestBody SkuInfo info){
        skuInfoService.saveSkuInfo(info);
        return Result.ok();
    }

    /**
     * 3. 商品上架
     */
    @GetMapping("/onSale/{skuId}")
    public Result onSale(@PathVariable("skuId")Long skuId){
        skuInfoService.onSale(skuId);
        return Result.ok();
    }

    /**
     * 4. 商品下架
     */
    @GetMapping("/cancelSale/{skuId}")
    public Result cancelSale(@PathVariable("skuId")Long skuId){
        skuInfoService.cancelSale(skuId);
        return Result.ok();
    }
}
