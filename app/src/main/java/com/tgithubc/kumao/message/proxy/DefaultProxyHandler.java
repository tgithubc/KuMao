package com.tgithubc.kumao.message.proxy;


import com.tgithubc.kumao.message.IObserver;
import com.tgithubc.kumao.message.MessageBus;
import com.tgithubc.kumao.message.message.Message;
import com.tgithubc.kumao.message.util.BusTool;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 普通消息InvocationHandler
 * Created by tc :)
 */
public class DefaultProxyHandler implements InvocationHandler {

    protected Class<? extends IObserver> mClazzType;

    private List<WeakReference<? extends IObserver>> mObservers;
    private AtomicInteger mSameTypeObservers = new AtomicInteger(0);

    public DefaultProxyHandler(List<WeakReference<? extends IObserver>> observers,
                               Class<? extends IObserver> clazzType) {
        this.mClazzType = clazzType;
        this.mObservers = observers;
    }

    public Object get() {
        return Proxy.newProxyInstance(mClazzType.getClassLoader(),
                new Class[]{mClazzType},
                this);
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) {
        for (WeakReference<? extends IObserver> wrf : mObservers) {
            Object ob = wrf.get();
            if (!mClazzType.isInstance(ob)) {
                continue;
            }
            try {
                Method invokeMethod = ob.getClass().getMethod(method.getName(),
                        method.getParameterTypes());
                Message.DecorateInfo info = BusTool.getDecorateInfo(invokeMethod);
                Message message = BusTool.obtainMessage(invokeMethod, args, info, ob);
                MessageBus.instance().dispatch(message);
            } catch (Exception e) {
                throw new RuntimeException(ob.getClass().getName()
                        + "has  no method :"
                        + method.getName() + e);
            }
        }
        return null;
    }

    /**
     * 获取同一个class类型观察者有多少个
     *
     * @return
     */
    public int getSameTypeObserverCount() {
        mSameTypeObservers.set(0);
        for (WeakReference weakReference : mObservers) {
            Object ob = weakReference.get();
            if (mClazzType.isInstance(ob)) {
                mSameTypeObservers.incrementAndGet();
            }
        }
        return mSameTypeObservers.get();
    }
}
