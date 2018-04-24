package com.tgithubc.kumao.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

/**
 * Created by tc :)
 */
public class RomStatusBarUtil {

    public static boolean isFlyme4Above = false;
    public static boolean isFlyme6Above = false;
    public static boolean isMiUi6Above = false;
    public static boolean isMiUi9Above = false;
    public static boolean isAndroidMOrAbove = false;


    public static void initRomInfo() {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        isAndroidMOrAbove = Build.VERSION.SDK_INT >= 23;
        isFlyme6Above = isFlymeV6OrAbove();
        if (isFlyme6Above) {
            return;
        }
        isFlyme4Above = isFlymeV4OrAbove();
        if (isFlyme4Above) {
            return;
        }
        isMiUi9Above = isMIUIV9OrAbove();
        if (isMiUi9Above) {
            return;
        }
        isMiUi6Above = isMIUIV6OrAbove();
        if (isMiUi6Above) {
            return;
        }
    }

    public static boolean isMiuiOrFlyme() {
        return isMiUi9Above || isFlyme4Above || isFlyme6Above || isMiUi6Above;
    }

    private static boolean isFlymeV4OrAbove() {
        String displayId = Build.DISPLAY;
        if (!android.text.TextUtils.isEmpty(displayId) && displayId.contains("Flyme")) {
            String[] displayIdArray = displayId.split(" ");
            for (String temp : displayIdArray) {
                //版本号4以上，形如4.x.
                if (temp.matches("^[4-9]\\.(\\d+\\.)+\\S*")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isFlymeV6OrAbove() {
        String displayId = Build.DISPLAY;
        if (!android.text.TextUtils.isEmpty(displayId) && displayId.contains("Flyme")) {
            String[] displayIdArray = displayId.split(" ");
            for (String temp : displayIdArray) {
                //版本号4以上，形如4.x.
                if (temp.matches("^[6-9]\\.(\\d+\\.)+\\S*")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isMIUIV6OrAbove() {
        String miuiVersionCodeStr = getSystemProperty("ro.miui.ui.version.code");
        if (!android.text.TextUtils.isEmpty(miuiVersionCodeStr)) {
            try {
                int miuiVersionCode = Integer.parseInt(miuiVersionCodeStr);
                if (miuiVersionCode >= 4) {
                    return true;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

    private static boolean isMIUIV9OrAbove() {
        String miuiVersionCodeStr = getSystemProperty("ro.miui.ui.version.code");
        if (!android.text.TextUtils.isEmpty(miuiVersionCodeStr)) {
            try {
                int miuiVersionCode = Integer.parseInt(miuiVersionCodeStr);
                if (miuiVersionCode >= 7) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static String getSystemProperty(String propName) {
        String line = null;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    public static void resetStatusBarWhite(Activity activity) {
        // Flyme4不管是Android6.0以上还是以下||Flyme6 Android6.0以下都按Flyme官方的辣鸡办法适配
        if ((!isFlyme6Above && isFlyme4Above)
                || (isFlyme6Above && !isAndroidMOrAbove)) {
            setFlyme(activity, false);
            return;
        }
        // miui6不管是Android6.0以上还是以下||miui9 Android6.0以下都按miui官方的辣鸡办法适配
        if ((!isMiUi9Above && isMiUi6Above)
                || (isMiUi9Above && !isAndroidMOrAbove)) {
            setMiui(activity, false);
            return;
        }
        // 按google的原生适配
        if (isAndroidMOrAbove) {
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

    public static void resetStatusBarBlack(Activity activity) {
        // Flyme4不管是Android6.0以上还是以下||Flyme6 Android6.0以下都按Flyme官方的办法适配
        if ((!isFlyme6Above && isFlyme4Above)
                || (isFlyme6Above && !isAndroidMOrAbove)) {
            setFlyme(activity, true);
            return;
        }
        // miui6不管是Android6.0以上还是以下||miui9 Android6.0以下都按miui官方的办法适配
        if ((!isMiUi9Above && isMiUi6Above)
                || (isMiUi9Above && !isAndroidMOrAbove)) {
            setMiui(activity, true);
            return;
        }
        // 按google的原生适配
        if (isAndroidMOrAbove) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
