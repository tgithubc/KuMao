package com.tgithubc.kumao;

import android.app.Application;
import android.content.Context;

import com.tgithubc.fresco_wapper.load.impl.FrescoImageLoader;
import com.tgithubc.kumao.db.DbCore;
import com.tgithubc.kumao.service.PlayManager;
import com.tgithubc.kumao.util.AppUtil;
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
        init();
    }

    public static Context getContext() {
        return mInstance.getApplicationContext();
    }

    private void init() {
        if (AppUtil.isMainProcess(this)) {
            RomUtil.initRomInfo();
            DbCore.getInstance().init(this);
            FrescoImageLoader.getInstance().initialize(this);
            PlayManager.getInstance().bindToService();
        }
    }
}