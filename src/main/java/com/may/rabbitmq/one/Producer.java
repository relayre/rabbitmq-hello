package com.may.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author May
 * @creat 2022-08-12 14:34
 */
public class Producer {

    //队列名称
    public static final String QUEUE_NAME = "hello";
    //发送消息
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.120");
        factory.setUsername("root");
        factory.setPassword("123456");

        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        //生成队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "helloworld";

        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.out.println("发送完毕");
    }
}
