package com.tgithubc.kumao.message.proxy;

import android.util.Pair;


import com.tgithubc.kumao.message.IObserver;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 粘性消息InvocationHandler
 * Created by tc :)
 */
public class StickyProxyHandler extends DefaultProxyHandler {

    private List<Pair<Class<? extends IObserver>, Pair<Method, Object[]>>> mStickyMethodMap;

    public StickyProxyHandler(List<Pair<Class<? extends IObserver>, Pair<Method, Object[]>>> methodMap,
                              List<WeakReference<? extends IObserver>> observers,
                              Class<? extends IObserver> clazzType) {
        super(observers, clazzType);
        this.mStickyMethodMap = methodMap;
    }

    public Object get() {
        return Proxy.newProxyInstance(mClazzType.getClassLoader(),
                new Class[]{mClazzType},
                this);
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) {
        // 存粘性消息的内存快照
        Pair<Method, Object[]> methodPair = new Pair<>(method, args);
        mStickyMethodMap.add(new Pair<Class<? extends IObserver>, Pair<Method, Object[]>>
                (mClazzType, methodPair));
        // 继续
        return super.invoke(object, method, args);
    }
}
