package com.may.rabbitmq.five;

import com.may.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author May
 * @creat 2022-08-13 8:12
 */
public class FanoutSend01 {
    private static final String EXCHANGE_NAME = "exchange";
    public static void main(String[] args) throws Exception {
        FanoutSend01.SendByAsync();
    }

    public static void SendByAsync() throws Exception {
        Channel channel = RabbitMQUtils.getChannel();
        String queue = channel.queueDeclare().getQueue();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        ConcurrentSkipListMap<Long,String> concurrentSkipListMap =
                new ConcurrentSkipListMap();
        ConfirmCallback ack = (l, b) -> {
            ConcurrentNavigableMap<Long, String> confirm = concurrentSkipListMap.headMap(l);
            if (b){
                confirm.clear();
            }else {
                confirm.remove(l);
            }
            System.out.println("发送成功:" + l);
        };
        ConfirmCallback nack = (l, b) -> {
            concurrentSkipListMap.get(l);
            System.out.println("发送失败:" + l);
        };
        channel.addConfirmListener(ack,nack);
        for (int i = 0; i < 1000; i++) {
            String msg = i+"";
            System.out.println(msg);
            channel.basicPublish(EXCHANGE_NAME,"", null,msg.getBytes());
            concurrentSkipListMap.put(channel.getNextPublishSeqNo(),msg);
        }
        System.out.println("发送成功");
    }
}
