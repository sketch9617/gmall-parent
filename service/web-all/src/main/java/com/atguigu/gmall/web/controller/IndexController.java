package com.atguigu.gmall.web.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import com.atguigu.gmall.web.feign.CategoryFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sketch
 * @date 2022/8/26 11:56
 * @description
 */
@Controller
public class IndexController {
    @Autowired
    CategoryFeignClient categoryFeignClient;

    @GetMapping({"/", "/index"})
    public String indexPage(Model model) {
        //远程调用product服务,查询所有分类
        Result<List<CategoryTreeTo>> result = categoryFeignClient.getAllCategoryWithTree();

        //调用成功,传输数据返回页面
        if (result.isOk()) {
            List<CategoryTreeTo> data = result.getData();
            model.addAttribute("list", data);
        }
        return "index/index";
    }
}
