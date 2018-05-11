package com.tgithubc.kumao.base;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by tc :)
 */
public abstract class BasePresenter<V extends IView> {

    private CompositeSubscription mSubscription;
    private V mView;
    private Class<? extends IView> mViewClass;

    public void attachView(V view) {
        this.mView = view;
        this.mViewClass = view.getClass();
        this.mSubscription = new CompositeSubscription();
        if (mViewClass.getInterfaces().length == 0) {
            throw new IllegalArgumentException("view must implement IView interface");
        }
    }

    public void detachView() {
        mView = null;
        removeSubscribe();
    }

    public V getView() {
        if (mView == null) {
            return ViewProxy.newInstance(mViewClass);
        }
        return mView;
    }

    private void removeSubscribe() {
        if (mSubscription != null && mSubscription.hasSubscriptions()) {
            mSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription s) {
        if (mSubscription != null) {
            mSubscription.add(s);
        }
    }

    private static class ViewProxy implements InvocationHandler {

        public static <V> V newInstance(Class<? extends IView> clazz) {
            return (V) Proxy.newProxyInstance(
                    clazz.getClassLoader(),
                    new Class[]{IView.class},
                    new ViewProxy());
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) {
            try {
                return method.invoke(o, objects);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}