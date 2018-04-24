package com.tgithubc.kumao;

import android.app.Application;

import com.tgithubc.kumao.util.RomUtil;

/**
 * Created by tc :)
 */
public class KuMao extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RomUtil.initRomInfo();
    }
}