package com.tgithubc.kumao.message.handler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * Created by tc :)
 */
public final class ThreadMessageHandler {

    private HandlerThread mHandlerThread;
    private Handler mHandler;

    public ThreadMessageHandler() {
        mHandlerThread = new HandlerThread("WorkThread");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    public ThreadMessageHandler(Looper looper) {
        mHandler = new Handler(looper);
    }

    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    public void postDelayed(Runnable runnable, long delayedTime) {
        mHandler.postDelayed(runnable, delayedTime);
    }
}