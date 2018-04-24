package com.tgithubc.kumao.base;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.tgithubc.kumao.util.ImmersedStatusBarHelper;

/**
 * 先只处理沉浸式，后面用装饰替代继承可能会好很多
 * Created by tc :)
 */
public class BaseActivity extends AppCompatActivity {

    private ImmersedStatusBarHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 如果需要动态改变，发给消息过来只要这个activity才能用helper处理
        mHelper = new ImmersedStatusBarHelper();
        mHelper.immersedStatusBar(this);
    }
}
