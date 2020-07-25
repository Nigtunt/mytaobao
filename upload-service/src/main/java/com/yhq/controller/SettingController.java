package com.yhq.controller;

import com.yhq.constant.Cons;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * @Author: YHQ
 * @Date: 2020/7/24 18:36
 */
@RestController
@RequestMapping("/setting")
public class SettingController {
    @RequestMapping("list")
    public String list(){
        return Cons.map.toString() + "\n" + Cons.cookie.toString();
    }

    @RequestMapping("/set")
    public String set(String key,String value){
        if (key.equals("cookie")){
            Cons.setCookie(value);
        }else {
            Cons.map.put(key,value);
        }
        return key+":"+value;
    }

}
