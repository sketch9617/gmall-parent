package com.atguigu.gmall.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author sketch
 * @date 2022/9/7 18:29
 * @description
 */
@SpringCloudApplication
@MapperScan("com.atguigu.gmall.user.mapper")
public class UserMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserMainApplication.class, args);
    }
}
