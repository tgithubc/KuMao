package com.tgithubc.kumao.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tgithubc.kumao.util.RomStatusBarUtil;
import com.tgithubc.kumao.util.SystemBarTintManager;


/**
 * 先只处理沉浸式，后面用装饰替代继承可能会好很多
 * Created by tc :)
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        // 6.0以上||miui9，21以上
        if (RomStatusBarUtil.isAndroidMOrAbove
                || (RomStatusBarUtil.isMiUi9Above && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (RomStatusBarUtil.isMiuiOrFlyme()) {// miui，flyme自己的处理方式,后面会处理
            // do noting
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4以上，6.0以下
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager manager = new SystemBarTintManager(this);
            manager.setStatusBarTintEnabled(true);
        }
        resetStatusBarColor(false);
    }

    // 这个有可能得动态去页面改变，后面想个办法抽合理一些
    private void resetStatusBarColor(boolean isDark) {
        if (isDark) {
            RomStatusBarUtil.resetStatusBarBlack(this);
        } else {
            RomStatusBarUtil.resetStatusBarWhite(this);
        }
    }
}
