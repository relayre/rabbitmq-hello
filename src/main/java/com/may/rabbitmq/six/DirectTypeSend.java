package com.may.rabbitmq.six;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @author May
 * @creat 2022-08-13 9:18
 */
public class DirectTypeSend {
    private static final String EXHANGE_NAME="direct";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.getChannel();
        channel.exchangeDeclare(EXHANGE_NAME,"direct");
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String msg = scan.next();
            System.out.println(msg);
            channel.basicPublish(EXHANGE_NAME,"warning",null,msg.getBytes());
        }
    }
}
