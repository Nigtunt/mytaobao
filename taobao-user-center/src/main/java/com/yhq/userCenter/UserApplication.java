package com.yhq.userCenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: YHQ
 * @Date: 2020/7/9 18:17
 */
@SpringBootApplication
@MapperScan("com.yhq.userCenter.dao")
public class UserApplication {
    public static void main(String args[]){
        SpringApplication.run(UserApplication.class,args);
    }
}
