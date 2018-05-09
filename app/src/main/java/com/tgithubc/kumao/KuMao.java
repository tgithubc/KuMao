package com.tgithubc.kumao;

import android.app.Application;

import com.tgithubc.fresco_wapper.load.impl.FrescoImageLoader;
import com.tgithubc.kumao.util.RomUtil;

/**
 * Created by tc :)
 */
public class KuMao extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RomUtil.initRomInfo();
        FrescoImageLoader.getInstance().initialize(this);
    }
}