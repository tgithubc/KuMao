package com.tgithubc.kumao.util;

import android.os.Build;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by tc :)
 */
public class RomUtil {

    public static boolean isFlyme4Above = false;
    public static boolean isFlyme6Above = false;
    public static boolean isMiUi6Above = false;
    public static boolean isMiUi9Above = false;
    public static boolean isAndroidMOrAbove = false;

    public static void initRomInfo() {
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
}
