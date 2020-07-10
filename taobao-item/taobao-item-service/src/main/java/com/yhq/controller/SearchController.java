package com.yhq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.rmi.server.RMIClassLoader;
import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/7/8 21:04
 */
@RestController
@RequestMapping("test")
public class SearchController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("search")
    public String search(String key){
        return restTemplate.getForObject("http://SEARCH-SERVICE/search?key=" + key, String.class);
    }
}
