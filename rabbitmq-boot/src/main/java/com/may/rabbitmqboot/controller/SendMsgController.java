package com.may.rabbitmqboot.controller;

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
 * @creat 2022-08-13 15:21
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {
    @Autowired
    public RabbitTemplate rabbitTemplate;
    @GetMapping("/rabbitmq/{msg}")
    public void send(@PathVariable String msg){
        log.info("当前时间为：{}，发送一条消息：{}",new Date().toString(),msg);
        rabbitTemplate.convertAndSend("X","XA","消息来自3s的队列"+msg);
        rabbitTemplate.convertAndSend("X","XB","消息来自5s的队列"+msg);
    }
    @GetMapping("/rabbitmq/{msg}/{ttltime}")
    public void send(@PathVariable String msg,@PathVariable String ttltime){
        log.info("当前时间为：{}，耗时：{}，发送一条消息：{}",new Date().toString(),ttltime,msg);
        rabbitTemplate.convertAndSend("X","XC","消息来自"+ttltime+"ms的队列"+msg,message -> {
            message.getMessageProperties().setExpiration(ttltime);
            return message;
        });
    }

}
