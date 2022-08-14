package com.may.rabbitmqboot.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @author May
 * @creat 2022-08-13 15:45
 */
@Slf4j
@Component
public class ReceiveDeadConsumer {
    @RabbitListener(queues = "QD")
    public void receive(Message message, Channel channel){
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到死信队列信息{}", new Date().toString(), msg);
    }
}
