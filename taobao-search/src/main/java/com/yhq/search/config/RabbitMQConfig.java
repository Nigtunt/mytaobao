package com.yhq.search.config;

import com.rabbitmq.client.Channel;
import com.yhq.search.service.AddSpuService;
import org.aspectj.weaver.ast.Var;
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
 * @Date: 2020/7/20 11:59
 */
//@Configuration
public class RabbitMQConfig {
    @Bean
    Queue queue(){
        return new Queue("searchInfo_queue");
    }
    /**
     * 接收消息
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory, AddSpuService spuService) {
        //加载处理消息A的队列
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        //设置接收多个队列里面的消息，这里设置接收队列A
        //假如想一个消费者处理多个队列里面的信息可以如下设置：
        //container.setQueues(queueA(),queueB(),queueC());
        //设置确认模式手工确认
        container.setQueues(queue());
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener(new MessageListenerAdapter() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                /*
                  通过basic.qos方法设置prefetch_count=1，
                  这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message，
                  换句话说,在接收到该Consumer的ack前,它不会将新的Message分发给它 */
                channel.basicQos(1);
                byte[] body = message.getBody();
                String s = new String(body);
                logger.info("接收处理队列消息:" + s);
                // 调用service将信息插入es中
                spuService.AddSpu(s);
                /*为了保证永远不会丢失消息，RabbitMQ支持消息应答机制。
                 当消费者接收到消息并完成任务后会往RabbitMQ服务器发送一条确认的命令，
                 然后RabbitMQ才会将消息删除。*/
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        });
        return container;
    }
}
