package com.may.rabbitmq.three;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

/**
 * @author May
 * @creat 2022-08-12 16:54
 */
public class Task01 {
    public static final String QUEUE_NAME="ACK_QUEUE";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {

            String msg = scan.next();
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes("utf-8"));
            System.out.println("生产者的消息");
        }
    }
}
