package com.may.rabbitmq.eight;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author May
 * @creat 2022-08-13 11:19
 */
public class DeadConsumer {
    private static final String EXCHANGE_DEAD="dead";
    private static final String QUEUE_DEAD="dead";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_DEAD, "direct");
        channel.queueDeclare(QUEUE_DEAD,false,false,false,null);
        channel.queueBind(QUEUE_DEAD,EXCHANGE_DEAD,"lisi");
        DeliverCallback deliverCallback = (s, delivery) -> {
            System.out.println("DeadConsumer接收的消息是："+new String(delivery.getBody()));
        };
        CancelCallback cancelCallback = s -> {
            System.out.println(s+"取消");
        };
        channel.basicConsume(QUEUE_DEAD,true,deliverCallback,cancelCallback);
    }
}
