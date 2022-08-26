package com.atguigu.gmall.product.api;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.atguigu.gmall.product.service.BaseCategory2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sketch
 * @date 2022/8/26 10:25
 * @description 前端远程调用
 */
@RestController
@RequestMapping("/api/inner/rpc/product")
public class CategoryApiController {


    @Autowired
    BaseCategory2Service baseCategory2Service;

    /**
     * 1. 查询所有分类并封装成树形菜单结构
     */
    @GetMapping("/category/tree")
    public Result getAllCategoryWithTree() {
        List<CategoryTreeTo> categoryTreeToList = baseCategory2Service.getAllCategoryWithTree();
        return Result.ok(categoryTreeToList);
    }
}
