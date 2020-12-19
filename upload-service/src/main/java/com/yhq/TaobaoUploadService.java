package com.yhq;

import com.yhq.constant.Cons;
import com.yhq.service.CangkuService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.misc.Cleaner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author: YHQ
 * @Date: 2020/6/10 11:09
 */
@SpringBootApplication
//@EnableEurekaClient
@EnableScheduling
@MapperScan("com.yhq.mapper")
@Slf4j
public class TaobaoUploadService {
    public static void main(String args[]) {
        SpringApplication.run(TaobaoUploadService.class, args);
    }

    @Autowired
    CangkuService service;
    @Autowired
    JavaMailSender sender;

    @Scheduled(cron = "0 0 23 * * ?")
    public void cache() {
        String result = service.Cache(Cons.map.get("realPath"));
        try {
            sendMessage(result + " time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(String str) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("915540781@qq.com");
        helper.setTo("915540781@qq.com");
        helper.setSubject("缓存通知");
        helper.setSentDate(new Date());
        helper.setText(str);
        sender.send(message);
    }
}
