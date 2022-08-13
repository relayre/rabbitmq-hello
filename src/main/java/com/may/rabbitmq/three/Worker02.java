package com.may.rabbitmq.three;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.may.rabbitmq.utils.SleepUtil;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author May
 * @creat 2022-08-12 17:00
 */
public class Worker02 {
    private static final String QUEUE_NAME="ACK_QUEUE";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtils.getChannel();
        channel.basicQos(2);
        System.out.println("c2等待时间较长");
        DeliverCallback deliverCallback = (s, delivery) -> {
            SleepUtil.sleep(30);
            System.out.println(new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };
        CancelCallback cancelCallback = s -> {
            System.out.println(s+"消息已取消");
        };
        channel.basicConsume(QUEUE_NAME,false,deliverCallback,cancelCallback);
    }
}
