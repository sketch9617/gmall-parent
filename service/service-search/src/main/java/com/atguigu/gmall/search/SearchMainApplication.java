package com.atguigu.gmall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sketch
 * @date 2022/9/5 14:03
 * @description
 */
@EnableElasticsearchRepositories  //开启ES的自动仓库功能。
@SpringCloudApplication
public class SearchMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchMainApplication.class,args);
        Map<Object, Object> map = Collections.synchronizedMap(new HashMap<>());
    }
}
