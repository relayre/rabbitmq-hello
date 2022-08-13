package com.may.rabbitmq.five;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author May
 * @creat 2022-08-13 8:10
 */
public class FanoutReceive02 {
    private static final String EXCHANGE_NAME = "exchange";
    public static void main(String[] args) throws Exception {
        System.out.println("R2....");
        Channel channel = RabbitMQUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue,EXCHANGE_NAME,"");
        DeliverCallback deliverCallback = (s, delivery) -> {
            System.out.println(new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };
        CancelCallback cancelCallback = s -> {
            System.out.println(s+"已取消");
        };
        channel.basicConsume(queue,false,deliverCallback,cancelCallback);
    }
}
