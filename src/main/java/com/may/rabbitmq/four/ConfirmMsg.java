package com.may.rabbitmq.four;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.MessageProperties;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author May
 * @creat 2022-08-12 19:11
 */
public class ConfirmMsg {
    public static void main(String[] args) throws Exception {
        //ConfirmMsg.publishMsgConfirmByOne();//耗时：2362
        //ConfirmMsg.publishMsgConfirmByMutiple();//耗时：140
        //ConfirmMsg.publishMsgAsync();//耗时：27
        ConfirmMsg.publishMsgAsyncAndNo();//耗时：45
    }

    /**
     * 单个确认消息
     * @throws Exception
     */
    private static void publishMsgConfirmByOne() throws Exception {
        Channel channel = RabbitMQUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String msg = i+"";
            channel.basicPublish("",queueName, MessageProperties.MINIMAL_PERSISTENT_BASIC,msg.getBytes());
            boolean b = channel.waitForConfirms();
            if (b) {
                System.out.println("消息发送成功。");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-begin));
    }

    /**
     * 批量确认
     */
    public static void publishMsgConfirmByMutiple() throws Exception {
        Channel channel = RabbitMQUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String msg = i+"";
            channel.basicPublish("",queueName,MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());
            if (i%100==0){
                boolean b = channel.waitForConfirms();
                if(b){

                    System.out.println(i+"发送成功。");
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-begin));
    }

    /**
     * 异步批量确认
     */
    public static void publishMsgAsync() throws  Exception{
        Channel channel = RabbitMQUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        //消息确认成功回调函数
        ConfirmCallback ackCallback = (l, b) -> {
            System.out.println("确认的消息"+l);
        };
        //消息确认不成功回调函数
        ConfirmCallback nackCallback = (l, b) -> {
            System.out.println("未确认的消息"+l);
        };
        //消息监听器
        channel.addConfirmListener(ackCallback,nackCallback);
        for (int i = 0; i < 1000; i++) {
            String msg = i+"";
            channel.basicPublish("",queueName,MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-begin));
    }

    /**
     * 监听异步批量确认
     */
    public static void publishMsgAsyncAndNo() throws  Exception{
        Channel channel = RabbitMQUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        channel.confirmSelect();
        ConcurrentSkipListMap<Long,String> concurrentSkipListMap =
                new ConcurrentSkipListMap();
        long begin = System.currentTimeMillis();
        //消息确认成功回调函数
        ConfirmCallback ackCallback = (l, b) -> {
            ConcurrentNavigableMap<Long, String> confirm = concurrentSkipListMap.headMap(l);
            if (b) {
                confirm.clear();
            }else {
                concurrentSkipListMap.remove(l);
            }
            System.out.println("确认的消息"+l);
        };
        //消息确认不成功回调函数
        ConfirmCallback nackCallback = (l, b) -> {
            System.out.println(concurrentSkipListMap.get(l));
            System.out.println("未确认的消息"+l);
        };
        //消息监听器
        channel.addConfirmListener(ackCallback,nackCallback);
        for (int i = 0; i < 1000; i++) {
            String msg = i+"";
            System.out.println(msg);
            channel.basicPublish("",queueName,MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());
            concurrentSkipListMap.put(channel.getNextPublishSeqNo(),msg);
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-begin));
    }
}
