package com.may.rabbitmqboot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author May
 * @creat 2022-08-14 13:01
 */
@Configuration
public class ConfirmConfig {
    public static final String QUEUE_NAME="confirm.queue";
    public static final String EXCHANGE_NAME="confirm.exchange";
    public static final String ROUTING_KEY="confirm";

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }
    @Bean
    public Queue queueConfirm(){
        return new Queue(QUEUE_NAME);
    }
    @Bean
    public Binding bindingConfirm(@Qualifier("queueConfirm")Queue queueConfirm,
                                  @Qualifier("exchange")DirectExchange exchange){
        return BindingBuilder.bind(queueConfirm).to(exchange).with(ROUTING_KEY);
    }

}
