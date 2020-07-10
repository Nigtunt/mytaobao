package com.yhq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: YHQ
 * @Date: 2020/6/10 11:09
 */
@SpringBootApplication
//@EnableEurekaClient
public class TaobaoUploadService {
    public static void main(String args[]){
        SpringApplication.run(TaobaoUploadService.class,args);
    }


}
