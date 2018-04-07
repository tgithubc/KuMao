package com.tgithubc.kumao.widget;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class WeakWrapperHandler extends Handler {
    
    public interface MessageHandler {
        void handleMessage(Message msg);
    }

    private WeakReference<MessageHandler> mMessageHandler;

    public WeakWrapperHandler(MessageHandler msgHandler) {
        mMessageHandler = new WeakReference<>(msgHandler);
    }

    @Override
    public void handleMessage(Message msg) {
        MessageHandler realHandler = mMessageHandler.get();
        if (realHandler != null) {
            realHandler.handleMessage(msg);
        }
    }
}
