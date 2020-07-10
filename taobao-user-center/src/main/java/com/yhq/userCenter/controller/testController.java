package com.yhq.userCenter.controller;

import com.yhq.userCenter.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Author: YHQ
 * @Date: 2020/7/9 18:35
 */
@RestController
@RequestMapping
public class testController {
    @RequestMapping("/test")
    public void test(HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Enumeration<String> attributeNames = request.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()){
            System.out.println(attributeNames.nextElement());
            System.out.println(request.getSession().getAttribute("SPRING_SECURITY_CONTEXT"));
        }
        System.out.println(authentication);
    }
    @RequestMapping("/user")
    public String user(){
        return "user";
    }
    @RequestMapping("/admin")
    public String admin(User u){
        return "admin";
    }

}
