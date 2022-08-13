package com.may.rabbitmq.eight;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @author May
 * @creat 2022-08-13 11:18
 */
public class NormalConsumer {
    private static final String EXCHANGE_NORMAL="normal";
    private static final String EXCHANGE_DEAD="dead";
    private static final String QUEUE_NORMAL="normal";
    private static final String QUEUE_DEAD="dead";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NORMAL, "direct");
        channel.exchangeDeclare(EXCHANGE_DEAD, "direct");
        //设置声明由正常队列宕机时将消息传给死信队列的参数
        Map<String,Object> map = new HashMap();
        //死信交换机
        map.put("x-dead-letter-exchange",EXCHANGE_DEAD);
        //死信routingkey
        map.put("x-dead-letter-routing-key","lisi");
        //队列最大长度
//        map.put("x-max-length",6);
//        正常队列
        channel.queueDeclare(QUEUE_NORMAL,false,false,false,map);
//        死信队列
        channel.queueDeclare(QUEUE_DEAD,false,false,false,null);
        channel.queueBind(QUEUE_NORMAL,EXCHANGE_NORMAL,"zhangsan");
//        绑定死信队列
        channel.queueBind(QUEUE_DEAD,EXCHANGE_DEAD,"lisi");
        DeliverCallback deliverCallback = (s, delivery) -> {
            String str = new String(delivery.getBody());
            if (str.equals("消息5")){
                channel.basicReject(delivery.getEnvelope().getDeliveryTag(),false);
                System.out.println("接收的消息是：5");
            }else {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
                System.out.println("NormalConsumer接收的消息是："+new String(delivery.getBody()));
            }
        };
        CancelCallback cancelCallback = s -> {
            System.out.println(s+"取消");
        };
        channel.basicConsume(QUEUE_NORMAL,false,deliverCallback,cancelCallback);
    }
}
