package com.may.rabbitmqboot.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author May
 * @creat 2022-08-14 14:11
 */
@Component
@Slf4j
public class ConfirmConsumer {
    @RabbitListener(queues = "confirm.queue")
    public void recieve(Message message){
        String msg = new String(message.getBody());
        System.out.println(msg);
        log.info("接收到的消息是{}",msg);
    }

}
