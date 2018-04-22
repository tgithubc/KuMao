package com.tgithubc.kumao.message;

import android.os.Looper;
import android.util.Log;
import android.util.Pair;


import com.tgithubc.kumao.message.handler.ThreadMessageHandler;
import com.tgithubc.kumao.message.message.Message;
import com.tgithubc.kumao.message.message.RunThread;
import com.tgithubc.kumao.message.proxy.DefaultProxyHandler;
import com.tgithubc.kumao.message.proxy.StickyProxyHandler;
import com.tgithubc.kumao.message.util.BusTool;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Bus
 * Created by tc :)
 */
public class MessageBus {

    private static final String TAG = "MessageBus";

    private ThreadMessageHandler mMainHandler, mWorkHandler;
    // 观察者集合
    private List<WeakReference<? extends IObserver>> mObservers;
    // 每一个事件接口，对应一个handler处理对象，循环处理所有相关订阅者的方法调用
    private Map<Class<? extends IObserver>, DefaultProxyHandler> mProxyMap;
    private Map<Class<? extends IObserver>, StickyProxyHandler> mStickyProxyMap;
    // 这是个不太友好的数据结构，为了实现粘性消息的顺序激活，用了有序的list，多层pair为了记录方法信息
    private List<Pair<Class<? extends IObserver>, Pair<Method, Object[]>>> mStickyMethodMap;

    private MessageBus() {
        mObservers = new CopyOnWriteArrayList<>();
        mProxyMap = new ConcurrentHashMap<>();
        mStickyMethodMap = new CopyOnWriteArrayList<>();
        mStickyProxyMap = new ConcurrentHashMap<>();
        mWorkHandler = new ThreadMessageHandler();
        mMainHandler = new ThreadMessageHandler(Looper.getMainLooper());
    }

    private static class SingletonHolder {
        private static final MessageBus BUS = new MessageBus();
    }

    public static MessageBus instance() {
        return SingletonHolder.BUS;
    }

    /**
     * 获取接收普通消息对象的代理
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends IObserver> T getDefault(Class<T> clazz) {
        return getProxy(clazz, false);
    }

    /**
     * 获取接收粘性消息对象的代理
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends IObserver> T getSticky(Class<T> clazz) {
        return getProxy(clazz, true);
    }

    /**
     * 分发消息
     *
     * @param message
     */
    public void dispatch(Message message) {
        @RunThread
        int runThread = message.decorateInfo.runThread;
        long delayedTime = message.decorateInfo.delayedTime;

        switch (runThread) {
            case RunThread.MAIN:
                if (delayedTime > 0) {
                    mMainHandler.postDelayed(message, delayedTime);
                } else {
                    if (isMainThread()) {
                        message.run();
                    } else {
                        mMainHandler.post(message);
                    }
                }
                break;
            case RunThread.BACKGROUND:
                if (delayedTime > 0) {
                    mWorkHandler.postDelayed(message, delayedTime);
                } else {
                    if (!isMainThread()) {
                        message.run();
                    } else {
                        mWorkHandler.post(message);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 注册
     *
     * @param observer
     */
    public void register(IObserver observer) {
        if (observer == null || hasRegistered(observer)) {
            Log.w(TAG, "observer == null or has been registered");
            return;
        }
        mObservers.add(new WeakReference<>(observer));
        Log.d(TAG, "add size :" + mObservers.size() + ",mObservers :" + mObservers);
        handlerStickyMessage(observer);
    }

    /**
     * 解注册
     *
     * @param observer
     */
    public void unRegister(IObserver observer) {
        if (observer == null) {
            return;
        }
        for (WeakReference<? extends IObserver> ref : mObservers) {
            IObserver ob = ref.get();
            if (observer.equals(ob) || ob == null) {
                mObservers.remove(ref);
            }
        }

        Iterator iterator = mProxyMap.keySet().iterator();
        while (iterator.hasNext()) {
            Class type = (Class) iterator.next();
            if (type.isInstance(observer)
                    && mProxyMap.get(type).getSameTypeObserverCount() == 0) {
                iterator.remove();
            }
        }

        if (mObservers.size() == 0) {
            mMainHandler.stop();
            mWorkHandler.stop();
        }
        Log.d(TAG, "remove size :" + mObservers.size() + ",mObservers :" + mObservers);
    }

    /**
     * 移除指定粘性消息
     *
     * @param stickClazz
     */
    public void removeStickyMessage(Class<? extends IObserver> stickClazz) {
        mStickyProxyMap.remove(stickClazz);
        for (Pair<Class<? extends IObserver>, Pair<Method, Object[]>> pair : mStickyMethodMap) {
            if (stickClazz.equals(pair.first)) {
                mStickyMethodMap.remove(pair);
            }
        }
    }

    /**
     * 获取代理对象
     *
     * @param clazz
     * @param sticky
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T extends IObserver> T getProxy(Class<T> clazz, boolean sticky) {
        DefaultProxyHandler handler;
        if (sticky) {
            handler = mStickyProxyMap.get(clazz);
            if (handler == null) {
                handler = new StickyProxyHandler(mStickyMethodMap, mObservers, clazz);
                mStickyProxyMap.put(clazz, (StickyProxyHandler) handler);
            }
        } else {
            handler = mProxyMap.get(clazz);
            if (handler == null) {
                handler = new DefaultProxyHandler(mObservers, clazz);
                mProxyMap.put(clazz, handler);
            }
        }
        return (T) handler.get();
    }

    /**
     * 处理粘性消息
     *
     * @param observer
     */
    private void handlerStickyMessage(IObserver observer) {
        // ob对应的接口方法集合
        List<Class<? extends IObserver>> list = BusTool.getAllMethodInterfaces(observer);
        for (Pair<Class<? extends IObserver>, Pair<Method, Object[]>> pair : mStickyMethodMap) {
            if (list.contains(pair.first)) {
                Pair<Method, Object[]> methodPair = pair.second;
                try {
                    // 具体方法快照
                    Method method = methodPair.first;
                    Object[] args = methodPair.second;
                    // obMethod
                    Method obMethod = observer.getClass().getMethod(method.getName(), method.getParameterTypes());
                    Message.DecorateInfo info = BusTool.getDecorateInfo(obMethod);
                    // 是否粘性标示
                    if (info.isSticky) {
                        // 生成message去分发
                        Message message = BusTool.obtainMessage(method, args, info, observer);
                        dispatch(message);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 是否注册
     *
     * @param observer
     * @return
     */
    private boolean hasRegistered(IObserver observer) {
        for (WeakReference ref : mObservers) {
            IObserver o = (IObserver) ref.get();
            if (observer.equals(o)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
