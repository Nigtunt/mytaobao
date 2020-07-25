package com.yhq.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: YHQ
 * @Date: 2020/7/20 11:54
 */
@Configuration
public class RabbitMQConfig {

    // 声明队列
    @Bean
    public Queue queue(){

        return new Queue("searchInfo_queue");
    }
}
