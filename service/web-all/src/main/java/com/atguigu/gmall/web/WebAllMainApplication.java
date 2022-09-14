package com.atguigu.gmall.web;

import com.atguigu.gmall.common.annotation.EnableAutoFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sketch
 * @date 2022/8/26 11:26
 * @description
 */
@EnableAutoFeignInterceptor
@SpringCloudApplication
@EnableFeignClients(basePackages = {
        "com.atguigu.gmall.feign.item",
        "com.atguigu.gmall.feign.product",
        "com.atguigu.gmall.feign.search",
        "com.atguigu.gmall.feign.cart"
}) //只会扫描主程序所在的子包
public class WebAllMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAllMainApplication.class, args);
    }
}