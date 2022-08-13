package com.may.rabbitmq.five;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * 扇出类型交换机无routingkey
 * @author May
 * @creat 2022-08-13 8:10
 */
public class FanoutReceive01 {
    //定义交换机名称
    private static final String EXCHANGE_NAME = "exchange";
    public static void main(String[] args) throws Exception {
        System.out.println("R1....");
//        创建工厂连接信道
        Channel channel = RabbitMQUtils.getChannel();
//        声明定义交换机参数1：交换机名称，2：交换机类型
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
//        非持久化队列
        String queue = channel.queueDeclare().getQueue();
//        队列绑定1、队列名称，2、交换机名称，3、routingkey
        channel.queueBind(queue,EXCHANGE_NAME,"");
//        接收消息的回调
        DeliverCallback deliverCallback = (s, delivery) -> {
            System.out.println(new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        };
//        消息取消的回调
        CancelCallback cancelCallback = s -> {
            System.out.println(s+"已取消");
        };
//        消费者消费
        channel.basicConsume(queue,false,deliverCallback,cancelCallback);
    }
}
