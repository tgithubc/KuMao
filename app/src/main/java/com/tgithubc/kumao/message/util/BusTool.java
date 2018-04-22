package com.tgithubc.kumao.message.util;

import android.support.annotation.NonNull;


import com.tgithubc.kumao.message.IObserver;
import com.tgithubc.kumao.message.message.Decorate;
import com.tgithubc.kumao.message.message.Message;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc :)
 */
public class BusTool {

    /**
     * 获取方法的修饰注解
     *
     * @param invokeMethod
     * @return
     */
    public static Message.DecorateInfo getDecorateInfo(Method invokeMethod) {
        Decorate annotation = invokeMethod.getAnnotation(Decorate.class);
        Message.DecorateInfo info = new Message.DecorateInfo();
        if (annotation != null) {
            info.runThread = annotation.runThread();
            info.delayedTime = annotation.delayedTime();
            info.isSticky = annotation.isSticky();
        }
        return info;
    }

    /**
     * 构造一个消息
     *
     * @param invokeMethod 消息要处理的方法
     * @param args         方法参数
     * @param info         装饰附加属性
     * @param ob           消息方法执行的观察者
     * @return
     */
    @NonNull
    public static Message obtainMessage(Method invokeMethod, Object[] args,
                                        Message.DecorateInfo info, Object ob) {
        Message message = new Message();
        message.args = args;
        message.observer = ob;
        message.invokedMethod = invokeMethod;
        message.decorateInfo = info;
        return message;
    }

    /**
     * 获取观察者implements的所有接口
     *
     * @param observer
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Class<? extends IObserver>> getAllMethodInterfaces(IObserver observer) {
        Class[] interfaces = observer.getClass().getInterfaces();
        List<Class<? extends IObserver>> arrayList = new ArrayList<>();
        for (Class clazz : interfaces) {
            if (IObserver.class.isAssignableFrom(clazz)) {
                arrayList.add(clazz);
            }
        }
        return arrayList;
    }
}
