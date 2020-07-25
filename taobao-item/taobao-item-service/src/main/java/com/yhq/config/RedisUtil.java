package com.yhq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: YHQ
 * @Date: 2020/7/19 21:48
 */
@Component
public class RedisUtil {

    static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void getTemplate(RedisTemplate<String,Object> redisTemplate){
        RedisUtil.redisTemplate = redisTemplate;
    }
}
