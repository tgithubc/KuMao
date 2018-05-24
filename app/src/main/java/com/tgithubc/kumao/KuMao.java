package com.tgithubc.kumao;

import android.app.Application;
import android.content.Context;

import com.tgithubc.fresco_wapper.load.impl.FrescoImageLoader;
import com.tgithubc.kumao.util.RomUtil;

/**
 * Created by tc :)
 */
public class KuMao extends Application {

    private static KuMao mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        RomUtil.initRomInfo();
        FrescoImageLoader.getInstance().initialize(this);
    }

    public static Context getContext() {
        return mInstance.getApplicationContext();
    }
}