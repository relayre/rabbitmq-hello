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
public class Worker01 {
    private static final String QUEUE_NAME="ACK_QUEUE";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtils.getChannel();
        //当参数为1时为不公平，大于1则为预取值
        channel.basicQos(3);
        System.out.println("c1等待时间较短");
        DeliverCallback deliverCallback = (s, delivery) -> {
            SleepUtil.sleep(1);
            System.out.println(new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };
        CancelCallback cancelCallback = s -> {
            System.out.println(s+"消息已取消");
        };
        channel.basicConsume(QUEUE_NAME,false,deliverCallback,cancelCallback);
    }
}
