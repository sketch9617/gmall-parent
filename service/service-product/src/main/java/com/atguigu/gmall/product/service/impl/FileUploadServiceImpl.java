package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.util.DateUtil;
import com.atguigu.gmall.product.config.minio.MinioProperties;
import com.atguigu.gmall.product.service.FileUploadService;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

/**
 * @author sketch
 * @date 2022/8/25 18:15
 * @description
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    MinioClient minioClient;
    @Autowired
    MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file) throws Exception {

        //1. 创建MinioClient
        //2. 先判断这个桶是否存在
        boolean gmall = minioClient.bucketExists(minioProperties.getBucketName());
        if(!gmall){
            //不存在创建
            minioClient.makeBucket(minioProperties.getBucketName());
        }

        //3. 给桶里面上传文件
        String name = file.getName(); //input的name的名
        //得到一个唯一文件名
        String dateStr = DateUtil.formatDate(new Date());
        String filename = UUID.randomUUID().toString().replace("-","")
                + "_" + file.getOriginalFilename(); //原始文件名
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();

        PutObjectOptions options = new PutObjectOptions(file.getSize(),-1L);
        options.setContentType(contentType);

        //4、文件上传
        minioClient.putObject(
                minioProperties.getBucketName(),
                dateStr+"/"+filename, //自己指定的唯一名
                inputStream,
                options
        );

        //5、返回刚才上传文件的可访问路径
        String url = minioProperties.getEndpointUrl()+"/"+minioProperties.getBucketName()+"/"+dateStr+"/"+filename;
        return url;
    }
}
