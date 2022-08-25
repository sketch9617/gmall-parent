package com.atguigu.gmall.product.config.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author sketch
 * @date 2022/8/25 18:28
 * @description
 */
@Configuration
public class MinioAutoConfiguration {

    @Autowired
    MinioProperties minioProperties;

    /**
     * 抽取自动注入 MinioClient
     */
    @Bean
    public MinioClient minioClient() throws Exception {

        //1、创建Minio客户端
        MinioClient minioClient = new MinioClient(
                minioProperties.getEndpointUrl(),
                minioProperties.getAccessKey(),
                minioProperties.getSecretKey()
        );

        String bucketName = minioProperties.getBucketName();
        if(!minioClient.bucketExists(bucketName)){
            minioClient.makeBucket(bucketName);
        }

        return minioClient;
    }
}
