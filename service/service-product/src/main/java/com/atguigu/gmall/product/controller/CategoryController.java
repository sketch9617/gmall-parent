package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.product.BaseCategory1;
import com.atguigu.gmall.model.product.BaseCategory2;
import com.atguigu.gmall.product.service.BaseCategory1Service;
import com.atguigu.gmall.product.service.BaseCategory2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sketch
 * @date 2022/8/22 20:56
 */
@RestController
@RequestMapping("/admin/product")
public class CategoryController {

    @Autowired
    BaseCategory1Service baseCategory1Service;
    @Autowired
    BaseCategory2Service baseCategory2Service;

    /**
     * 1. 获取所有商品一级分类
     */
    @GetMapping("/getCategory1")
    public Result getCategory1() {
        List<BaseCategory1> list = baseCategory1Service.list();
        return Result.ok(list);
    }

    /**
     * 2. 获取所有一级分类下的二级分类
     */
    @GetMapping("/getCategory2/{c1Id}")
    public Result getCategory2(@PathVariable("c1Id")Long c1Id){
        //查询 父分类id是c1Id 的所有二级分类
        List<BaseCategory2> category2s = baseCategory2Service.getCategory1Child(c1Id);
        return Result.ok(category2s);
    }
}
