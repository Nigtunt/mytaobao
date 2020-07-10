package com.yhq.controller;

import com.yhq.common.execption.ExecptionEnum;
import com.yhq.common.execption.SystemExecption;
import com.yhq.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/6/10 11:11
 */
@RestController
@RequestMapping("upload")
public class ImageController {
    @Autowired
    UploadService uploadSerivce;

    @PostMapping("image")
    public ResponseEntity<String> image(MultipartFile file){
        String url= uploadSerivce.upload(file);
        if (StringUtils.isEmpty(url)){
            throw new SystemExecption(ExecptionEnum.UPLOAD_EXECPTION);
        }
        return ResponseEntity.ok(url);
    }
    @PostMapping("images")
    public ResponseEntity<List<String>> images(MultipartFile[] files){
        List<String> urls = uploadSerivce.uploads(files);
        if (StringUtils.isEmpty(urls)){
            throw new SystemExecption(ExecptionEnum.UPLOAD_EXECPTION);
        }
        return ResponseEntity.ok(urls);
    }
    @GetMapping("test")
    public ResponseEntity<String> test(String test){
        return ResponseEntity.ok(test);
    }
}
