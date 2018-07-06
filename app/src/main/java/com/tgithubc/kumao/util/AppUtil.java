package com.tgithubc.kumao.util;


import android.app.ActivityManager;
import android.content.Context;

public class AppUtil {

    public static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
                break;
            }
        }
        return processName;
    }

    public static boolean isMainProcess(Context context) {
        return context.getPackageName().equals(getCurrentProcessName(context));
    }
}
