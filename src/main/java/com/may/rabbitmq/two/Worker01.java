package com.may.rabbitmq.two;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author May
 * @creat 2022-08-12 15:24
 */
public class Worker01 {
    //队列名称
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtils.getChannel();

        DeliverCallback deliverCallback = (s, delivery) -> {
            System.out.println(new String(delivery.getBody()));
        };
        CancelCallback cancelCallback = s -> {
            System.out.println(s+"消息取消");
        };

        System.out.println("C2....");
        //接收消息
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

    }


}
