package com.may.rabbitmqboot.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author May
 * @creat 2022-08-13 21:46
 */
@Configuration
public class DelayQueueConfig {
    public static final String DELAY_QUEUE_NAME="delay.queue";
    public static final String DELAY_EXCHANGE_NAME="delay.exchange";
    public static final String DELAY_ROUTING_KEY="delay.routingkey";
    //声明交换机
    @Bean
    public CustomExchange delayExchange(){

        Map map =new HashMap();
        map.put("x-delayed-type","direct");
        return new CustomExchange(DELAY_EXCHANGE_NAME,"x-delayed-message",true,false,map);
    }
    @Bean
    public Queue queueDelay(){
        return new Queue(DELAY_QUEUE_NAME);
    }
    @Bean
    public Binding bindingDelay(@Qualifier("queueDelay")Queue queueDelay,
                                @Qualifier("delayExchange")CustomExchange delayExchange){
        return BindingBuilder.bind(queueDelay).to(delayExchange).with(DELAY_ROUTING_KEY).noargs();
    }
}
