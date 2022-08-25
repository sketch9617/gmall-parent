package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.service.FileUploadService;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * @author sketch
 * @date 2022/8/24 19:06
 * @description 文件上传API
 */
@RestController
@RequestMapping("/admin/product")
public class FileuploadController {

    @Autowired
    FileUploadService fileUploadService;

    /**
     * minio文件上传
     */
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file) throws Exception {
        String url = fileUploadService.upload(file);
        return Result.ok(url);
    }
}
