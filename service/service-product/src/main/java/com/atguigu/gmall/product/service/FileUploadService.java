package com.atguigu.gmall.product.service;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author sketch
 * @date 2022/8/25 18:15
 * @description 文件上传的API
 */
public interface FileUploadService {
    /**
     * minio文件上传
     * @param file
     * @return
     */
    String upload(MultipartFile file) throws Exception;
}
