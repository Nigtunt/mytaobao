package com.yhq.gateWay;

import com.sun.glass.ui.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ServerWebExchange;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.function.Predicate;

/**
 * @Author: YHQ
 * @Date: 2020/9/8 13:26
 */
@SpringBootApplication
@EnableEurekaClient
public class gate {
    public int findString(String[] words, String s) {
        int start = 0;
        int end = words.length-1;

        while(start<=end){
            int middle = start + (end - start ) / 2;
            int i = s.compareTo(words[middle]);
            if(words[middle].equals("") && !words[middle].equals(s)){
                start++;
                continue;
            }else if(words[end].equals(s)){
                return end;
            }
            if(i==0) return middle;
            else if(i<0){
                start = middle + 1;
            }else{
                end = middle - 1;
            }
        }
        return -1;
    }
    public static void main(String args[]){
//        SpringApplication.run(gate.class,args);
        gate g = new gate();
        g.findString(new String[]{"at", "", "", "", "ball", "", "", "car", "", "", "dad", "", ""
        },"ball");
    }

}
