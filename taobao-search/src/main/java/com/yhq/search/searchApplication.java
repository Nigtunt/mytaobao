package com.yhq.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: YHQ
 * @Date: 2020/7/8 17:45
 */
@SpringBootApplication
@MapperScan("com.yhq.search.dao")
@EnableEurekaClient
public class searchApplication {

    public static void main(String args[]){
        SpringApplication.run(searchApplication.class,args);
    }
}
