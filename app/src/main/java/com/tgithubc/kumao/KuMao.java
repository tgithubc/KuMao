package com.tgithubc.kumao;

import android.app.Application;
import android.content.Context;

import com.tgithubc.fresco_wapper.load.impl.FrescoImageLoader;
import com.tgithubc.kumao.db.DbCore;
import com.tgithubc.kumao.util.RomUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by tc :)
 */
public class KuMao extends Application {

    private static KuMao mInstance;
    private static ExecutorService mExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        RomUtil.initRomInfo();
        FrescoImageLoader.getInstance().initialize(this);
        mExecutor = Executors.newCachedThreadPool();
        DbCore.getInstance().init(this);
    }

    public static Context getContext() {
        return mInstance.getApplicationContext();
    }

    public static ExecutorService getExecutorService() {
        return mExecutor;
    }

}