package com.tgithubc.kumao;

import android.app.Application;
import android.content.Context;

import com.tgithubc.fresco_wapper.load.impl.FrescoImageLoader;
import com.tgithubc.kumao.db.DbCore;
import com.tgithubc.kumao.service.PlayManager;
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
        DbCore.getInstance().init(this);
        PlayManager.getInstance().bindToService();
    }

    public static Context getContext() {
        return mInstance.getApplicationContext();
    }
}