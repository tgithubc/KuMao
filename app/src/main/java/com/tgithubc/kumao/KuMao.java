package com.tgithubc.kumao;

import android.app.Application;

import com.tgithubc.kumao.util.RomStatusBarUtil;

/**
 * Created by tc :)
 */
public class KuMao extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RomStatusBarUtil.initRomInfo();
    }
}