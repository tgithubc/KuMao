package com.tgithubc.kumao.message.message;


import java.lang.reflect.Method;

/**
 * 消息
 * Created by tc :)
 */
public class Message implements Runnable {

    public DecorateInfo decorateInfo;
    public Object observer;
    public Method invokedMethod;
    public Object[] args;

    @Override
    public void run() {
        try {
            if (invokedMethod != null && observer != null) {
                invokedMethod.invoke(observer, args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 消息装饰
     */
    public static class DecorateInfo {
        @RunThread
        public int runThread = RunThread.MAIN;
        public long delayedTime = 0L;
        public boolean isSticky = false;
    }
}