package com.may.rabbitmqboot.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author May
 * @creat 2022-08-13 14:19
 */
@Configuration
public class TTLQueueConfig {
    /*普通队列名称
    死信队列的名称
    普通交换机
    死信交换机*/
    public static final String X_EXCHANGE = "X";
    public static final String Y_EXCHANGE = "Y";
    public static final String NORMAL_QUEUEA = "QA";
    public static final String NORMAL_QUEUEB = "QB";
    public static final String DEAD_QUEUE = "QD";
    public static final String NEW_QUEUE = "QC";

    @Bean("xexchange")
    public DirectExchange xexchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean("yexchange")
    public DirectExchange yexchange() {
        return new DirectExchange(Y_EXCHANGE);
    }

    @Bean("queueA")
    public Queue queueA() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("x-dead-letter-exchange", Y_EXCHANGE);
        map.put("x-dead-letter-routing-key", "YD");
        map.put("x-message-ttl", 3000);
        return QueueBuilder.durable(NORMAL_QUEUEA).withArguments(map).build();
    }

    @Bean("queueB")
    public Queue queueB() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("x-dead-letter-exchange", Y_EXCHANGE);
        map.put("x-dead-letter-routing-key", "YD");
        map.put("x-message-ttl", 5000);
        return QueueBuilder.durable(NORMAL_QUEUEB).withArguments(map).build();
    }

    @Bean("queueD")
    public Queue queueD() {
        return new Queue(DEAD_QUEUE);
    }
    @Bean("queueC")
    public Queue queueC() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("x-dead-letter-exchange", Y_EXCHANGE);
        map.put("x-dead-letter-routing-key", "YD");
        return QueueBuilder.durable(NEW_QUEUE).withArguments(map).build();
    }

    @Bean
    public Binding bindingA(@Qualifier("queueA") Queue queueA,
                            @Qualifier("xexchange") DirectExchange xexchange) {
        return BindingBuilder.bind(queueA).to(xexchange).with("XA");
    }
    @Bean
    public Binding bindingB(@Qualifier("queueB") Queue queueB,
                            @Qualifier("xexchange") DirectExchange xexchange) {
        return BindingBuilder.bind(queueB).to(xexchange).with("XB");
    }
    @Bean
    public Binding bindingY(@Qualifier("queueD") Queue queueD,
                            @Qualifier("yexchange") DirectExchange yexchange) {
        return BindingBuilder.bind(queueD).to(yexchange).with("YD");
    }
    @Bean
    public Binding bindingC(@Qualifier("queueC") Queue queueC,
                            @Qualifier("xexchange") DirectExchange xexchange) {
        return BindingBuilder.bind(queueC).to(xexchange).with("XC");
    }
}
