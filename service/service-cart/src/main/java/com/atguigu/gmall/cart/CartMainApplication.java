package com.atguigu.gmall.cart;

import com.atguigu.gmall.common.annotation.EnableThreadPool;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sketch
 * @date 2022/9/8 20:34
 * @description
 */
@SpringCloudApplication
@EnableFeignClients(basePackages = "com.atguigu.gmall.feign.product")
@EnableThreadPool
public class CartMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartMainApplication.class, args);
    }
}
