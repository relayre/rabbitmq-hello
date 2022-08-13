package com.may.rabbitmq.eight;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.impl.AMQBasicProperties;

/**
 * @author May
 * @creat 2022-08-13 11:19
 */
public class Producer {
    private static final String EXCHANGE_NORMAL="normal";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NORMAL, "direct");
        AMQP.BasicProperties ttl = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 0; i < 10; i++) {
            String msg = "消息"+i;
            System.out.println(msg);
            channel.basicPublish(EXCHANGE_NORMAL,"zhangsan",ttl,msg.getBytes());
        }
    }
}
