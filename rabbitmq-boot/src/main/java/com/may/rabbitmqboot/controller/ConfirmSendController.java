package com.may.rabbitmqboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author May
 * @creat 2022-08-14 14:06
 */
@RestController
@RequestMapping("/confirm")
@Slf4j
public class ConfirmSendController {
    @Autowired
    public RabbitTemplate rabbitTemplate;
    //发消息
    @GetMapping("/rabbit/{msg}")
    public void sendMsg(@PathVariable String msg){
        rabbitTemplate.convertAndSend("confirm.exchange","confirm",msg);
        log.info("发送的是：{}",msg);
    }
}
