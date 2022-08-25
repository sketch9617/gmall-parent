package com.atguigu.gmall.product.config.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author sketch
 * @date 2022/8/24 21:36
 * @description Minio配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.minio")
public class MinioProperties {
    String endpointUrl;
    String accessKey;
    String secretKey;
    String bucketName;
}
