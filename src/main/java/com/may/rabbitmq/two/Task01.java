package com.may.rabbitmq.two;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @author May
 * @creat 2022-08-12 15:44
 */
public class Task01 {
    public static final String QUEUE_NAME = "hello";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.getChannel();
        /*
        队列名称
        是否持久化消息
        是否只供一个消费者消费
        是否自动删除
        其他参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()){
            String msg = scan.next();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("发送完毕"+msg);
        }
    }
}
