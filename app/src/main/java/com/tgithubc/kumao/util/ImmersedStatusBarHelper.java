package com.tgithubc.kumao.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by tc :)
 */

public class ImmersedStatusBarHelper {

    public void immersedStatusBar(Activity activity) {
        Window window = activity.getWindow();
        // 6.0以上||miui9，21以上
        if (RomUtil.isAndroidMOrAbove
                || (RomUtil.isMiUi9Above && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (RomUtil.isMiuiOrFlyme()) {// miui，flyme自己的处理方式,后面会处理
            // do noting
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4以上，6.0以下
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager manager = new SystemBarTintManager(activity);
            manager.setStatusBarTintEnabled(true);
        }
        resetStatusBarColor(activity, false);
    }

    public static void resetStatusBarColor(Activity activity, boolean isDark) {
        if (isDark) {
            resetStatusBarBlack(activity);
        } else {
            resetStatusBarWhite(activity);
        }
    }

    private static void resetStatusBarWhite(Activity activity) {
        // Flyme4不管是Android6.0以上还是以下||Flyme6 Android6.0以下都按Flyme官方的办法适配
        if ((!RomUtil.isFlyme6Above && RomUtil.isFlyme4Above)
                || (RomUtil.isFlyme6Above && !RomUtil.isAndroidMOrAbove)) {
            setFlyme(activity, false);
            return;
        }
        // miui6不管是Android6.0以上还是以下||miui9 Android6.0以下都按miui官方的办法适配
        if ((!RomUtil.isMiUi9Above && RomUtil.isMiUi6Above)
                || (RomUtil.isMiUi9Above && !RomUtil.isAndroidMOrAbove)) {
            setMiui(activity, false);
            return;
        }
        // 按google的原生适配
        if (RomUtil.isAndroidMOrAbove) {
            Window window = activity.getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
            return;
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.transparent);
    }


    @SuppressLint("InlinedApi")
    private static void resetStatusBarBlack(Activity activity) {
        // Flyme4不管是Android6.0以上还是以下||Flyme6 Android6.0以下都按Flyme官方的办法适配
        if ((!RomUtil.isFlyme6Above && RomUtil.isFlyme4Above)
                || (RomUtil.isFlyme6Above && !RomUtil.isAndroidMOrAbove)) {
            setFlyme(activity, true);
            return;
        }
        // miui6不管是Android6.0以上还是以下||miui9 Android6.0以下都按miui官方的办法适配
        if ((!RomUtil.isMiUi9Above && RomUtil.isMiUi6Above)
                || (RomUtil.isMiUi9Above && !RomUtil.isAndroidMOrAbove)) {
            setMiui(activity, true);
            return;
        }
        // 按google的原生适配
        if (RomUtil.isAndroidMOrAbove) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            return;
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(android.R.color.black);
    }

    private static void setMiui(Activity activity, boolean darkText) {
        try {
            Class<? extends Window> clazz = activity.getWindow().getClass();
            int darkModeFlag;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkText ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setFlyme(Activity activity, boolean darkText) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkText) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
