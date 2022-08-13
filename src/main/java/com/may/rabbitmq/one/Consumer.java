package com.may.rabbitmq.one;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

/**
 * @author May
 * @creat 2022-08-12 14:54
 */
public class Consumer {
    //队列名称
    public static final String QUEUE_NAME = "hello";
    //发送消息
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.getChannel();


        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(new String(message.getBody()));
        };

        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息被中断");
        };

        /*
        消费的队列名
        消费成功后是否自动应答 bool
        消费未成功的回调
        取消消费的回调
         */
        System.out.println("C1....");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
