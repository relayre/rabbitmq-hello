package com.may.rabbitmqboot.controller;

import com.may.rabbitmqboot.config.DelayQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author May
 * @creat 2022-08-13 22:04
 */
@Slf4j
@RestController
@RequestMapping("/delay")
public class DelayedSendController {
    @Autowired
    public RabbitTemplate rabbitTemplate;
    @GetMapping("/rabbitmq/{msg}")
    public void send(@PathVariable String msg){
        log.info("当前时间为：{}，发送一条消息：{}",new Date().toString(),msg);
        rabbitTemplate.convertAndSend("X","XA","消息来自3s的队列"+msg);
        rabbitTemplate.convertAndSend("X","XB","消息来自5s的队列"+msg);
    }
    @GetMapping("/rabbitmq/{msg}/{delaytime}")
    public void send(@PathVariable String msg,@PathVariable String delaytime){
        log.info("当前时间为：{}，耗时：{}，发送一条消息：{}",new Date().toString(),delaytime,msg);
        rabbitTemplate.convertAndSend(DelayQueueConfig.DELAY_EXCHANGE_NAME,DelayQueueConfig.DELAY_ROUTING_KEY,"消息来自"+delaytime+"ms的队列"+msg, message -> {
            message.getMessageProperties().setExpiration(delaytime);
            return message;
        });
    }

}
