package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.SpuInfo;
import com.atguigu.gmall.product.service.SpuInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author sketch
 * @date 2022/8/25 11:29
 * @description Sku管理
 */
@RestController
@RequestMapping("/admin/product")
public class SpuController {
    @Autowired
    SpuInfoService spuInfoService;

    // TODO: 2022/8/25 1

    /**
     * 1. 分页获取spu
     */

    /**
     * 2. 保存spu信息
     */

    /**
     * 3. 根据spu_id查询spu所有图片
     */

}
