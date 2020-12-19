package com.yhq.search.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhq.search.service.SearchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: YHQ
 * @Date: 2020/7/8 21:09
 */
@RestController

@RequestMapping()
public class searchController {
    @Autowired
    SearchingService searchingService;

    @GetMapping(value = "search")

    public String search(String key) throws IOException {
        return new ObjectMapper().writeValueAsString(searchingService.search(key,0,0));
    }

    @GetMapping("test")
    public String test(){
        return "test";
    }
}
