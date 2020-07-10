package com.yhq.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: YHQ
 * @Date: 2020/6/10 11:21
 */
@Service
public class UploadService {

//    public String upload(MultipartFile file){
//        String newName= "";
//        try {
//            String name = file.getOriginalFilename();
//            if (StringUtils.isEmpty(name))
//                return "";
//            newName = UUID.randomUUID().toString().replaceAll("-","")
//                    .concat(name.substring(name.lastIndexOf(".")));
//            String locationPath = ResourceUtils.getURL("classpath:").getPath();
//            locationPath = URLDecoder.decode(locationPath,"utf-8");
//            File file1 = new File(locationPath + "/static/upload/" + newName);
//            file.transferTo(file1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "http://192.168.0.104:8082/upload/"+newName;
//    }
    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    public String upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String name="";
        try {
            InputStream inputStream = file.getInputStream();
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(
                    inputStream,
                    file.getSize(),
                    StringUtils.substringAfterLast(originalFilename, "."),
                    null
            );

            name = storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "http://123.56.106.111:39011/"+name;
    }

    public List<String> uploads(MultipartFile[] file) {
        List<String> list = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            list.add(upload(multipartFile));
        }
        return list;
    }
}
