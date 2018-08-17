package com.tgithubc.kumao.base;

import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.tgithubc.kumao.message.IObserver;
import com.tgithubc.kumao.message.MessageBus;
import com.tgithubc.kumao.observer.IKuMaoObserver;
import com.tgithubc.kumao.util.DPPXUtil;
import com.tgithubc.kumao.util.ImmersedStatusBarHelper;

/**
 * 先只处理沉浸式，后面用装饰替代继承可能会好很多
 * Created by tc :)
 */
public class BaseActivity extends AppCompatActivity {

    private ImmersedStatusBarHelper mHelper;
    private IObserver mChangeStatusBarObserver = new IKuMaoObserver.IChangeStatusBarObserver() {
        @Override
        public void onChangeStatusBar(boolean isDark) {
            if (mHelper != null) {
                mHelper.resetStatusBarTextColor(BaseActivity.this, isDark);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // 如果需要动态改变，需要发个消息过来，只有这个activity才能用mHelper处理动态改变沉浸式文字不同颜色
        mHelper = new ImmersedStatusBarHelper();
        mHelper.immersedStatusBar(this);
        MessageBus.instance().register(mChangeStatusBarObserver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adjustTitleBarHeight();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageBus.instance().unRegister(mChangeStatusBarObserver);
    }

    /**
     * activity调整顶部沉浸式TitleBar高度
     */
    private void adjustTitleBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View titleBar = getWindow().getDecorView().findViewWithTag("titleBar");
            if (titleBar != null) {
                ViewGroup.LayoutParams params = titleBar.getLayoutParams();
                int h = DPPXUtil.getStatusBarHeight();
                if (params != null) {
                    params.height = h + DPPXUtil.dp2px(45);
                    titleBar.setPadding(0, h, 0, 0);
                    titleBar.setLayoutParams(params);
                }
            }
        }
    }
}
