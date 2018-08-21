package com.tgithubc.kumao.base;

import java.lang.ref.WeakReference;
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
    private V mProxyView;
    private WeakReference<V> mWeakReference;

    public void attachView(V view) {
        if (view.getClass().getInterfaces().length == 0) {
            throw new IllegalArgumentException("view must implement IView interface");
        }
        mWeakReference = new WeakReference<>(view);
        mProxyView = (V) Proxy.newProxyInstance(
                view.getClass().getClassLoader(),
                view.getClass().getInterfaces(),
                new ViewProxy(view));
        mSubscription = new CompositeSubscription();
    }

    public void detachView() {
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
        removeSubscribe();
    }

    public V getView() {
        return mProxyView;
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

    private boolean isAttachView() {
        return mWeakReference != null && mWeakReference.get() != null;
    }

    private class ViewProxy implements InvocationHandler {

        private IView mView;

        ViewProxy(IView view) {
            this.mView = view;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] args) throws Throwable {
            if (isAttachView()) {
                return method.invoke(mView, args);
            }
            return null;
        }
    }
}