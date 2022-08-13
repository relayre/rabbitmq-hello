package com.may.rabbitmq.six;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author May
 * @creat 2022-08-13 9:19
 */
public class DirectTypeReceive01 {
    private static final String EXHANGE_NAME="direct";
    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtils.getChannel();
        channel.exchangeDeclare(EXHANGE_NAME,"direct");
        channel.queueDeclare("console",false,false,false,null);
        channel.queueBind("console",EXHANGE_NAME,"info");
        channel.queueBind("console",EXHANGE_NAME,"warning");
        DeliverCallback deliverCallback = (s, delivery) -> {
            System.out.println(new String(delivery.getBody()));
        };
        CancelCallback cancelCallback = s -> {
            System.out.println(s+"取消");
        };
        channel.basicConsume("console",false,deliverCallback,cancelCallback);
    }
}
