package com.yhq.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhq.entity.SpuBo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author: YHQ
 * @Date: 2020/7/20 11:44
 */
@Service
public class SearchInfoSenderService implements RabbitTemplate.ConfirmCallback {
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;
    @Autowired
    public SearchInfoSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
        objectMapper = new ObjectMapper();
    }


    public void sendSearchInfo(SpuBo bo) {
        @Data
        @AllArgsConstructor
        class SearchSpu {
            private String title;
            private String sub_title;
            private boolean saleable;
        }

        SearchSpu searchSpu = new SearchSpu(bo.getTitle(),bo.getSubTitle(),bo.isSaleable());
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("callbackSender UUID: " + correlationData.getId());
        String context = null;
        try {
            context = objectMapper.writeValueAsString(searchSpu);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        this.rabbitTemplate.convertAndSend("","searchInfo_queue", context, correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println("id:"+correlationData.getId()+":"+b);

        //TODO 失败处理
    }
}
