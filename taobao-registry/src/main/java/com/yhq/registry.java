package com.yhq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: YHQ
 * @Date: 2020/5/29 22:34
 */
@SpringBootApplication
@EnableEurekaServer
@RestController
public class registry {
    public static void main(String args[]){
        SpringApplication.run(registry.class,args);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
