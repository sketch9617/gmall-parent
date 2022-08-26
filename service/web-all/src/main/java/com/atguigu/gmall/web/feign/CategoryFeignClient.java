package com.atguigu.gmall.web.feign;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.CategoryTreeTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author sketch
 * @date 2022/8/26 17:53
 * @description 远程调用 service-product 微服务的功能
 */
@FeignClient("service-product")
@RequestMapping("/api/inner/rpc/product")
public interface CategoryFeignClient {
    @GetMapping("/category/tree")
    Result<List<CategoryTreeTo>> getAllCategoryWithTree();
}
