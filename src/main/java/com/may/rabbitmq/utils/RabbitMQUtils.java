package com.may.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @author May
 * @creat 2022-08-12 15:21
 */
public class RabbitMQUtils {

    /*
    连接工厂创建信道的工具类
     */
    public static Channel getChannel() throws Exception {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.120");
        factory.setUsername("root");
        factory.setPassword("123456");

        //创建连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        return channel;
    }

}
