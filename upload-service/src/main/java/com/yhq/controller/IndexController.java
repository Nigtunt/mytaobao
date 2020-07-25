package com.yhq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: YHQ
 * @Date: 2020/6/24 21:45
 */
@Controller
@RequestMapping
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "test";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/set")
    public String set(){
        return "set";
    }
}
