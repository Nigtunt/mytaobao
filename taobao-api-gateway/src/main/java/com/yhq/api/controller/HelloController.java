package com.yhq.api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: YHQ
 * @Date: 2020/7/15 20:45
 */
@RestController
@RequestMapping
public class HelloController {
    @RequestMapping("/admin/test")
    public String test(){
        return "admin test";
    }

    @RequestMapping("/hello/test")
    public String hello(){
        return "admin test";
    }
}
