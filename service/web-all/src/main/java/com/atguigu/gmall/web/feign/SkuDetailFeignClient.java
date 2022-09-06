package com.atguigu.gmall.web.feign;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.model.to.SkuDetailTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sketch
 * @date 2022/8/26 21:33
 * @description
 */
@FeignClient("service-item")
@RequestMapping("/api/inner/rpc/item")
public interface SkuDetailFeignClient {
    @GetMapping("/skudetail/{skuId}")
    Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId") Long skuId);
}