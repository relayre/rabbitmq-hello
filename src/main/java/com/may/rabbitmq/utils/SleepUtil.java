package com.may.rabbitmq.utils;

/**
 * @author May
 * @creat 2022-08-12 17:07
 */

/**
 * 睡眠工具类
 */
public class SleepUtil {
    public static void sleep(int second){
        try {
            Thread.sleep(1000*second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
