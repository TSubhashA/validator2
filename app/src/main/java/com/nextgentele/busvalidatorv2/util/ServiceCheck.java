package com.nextgentele.busvalidatorv2.util;

import android.app.ActivityManager;
import android.content.Context;

public class ServiceCheck {

    public static boolean isServiceRunning(Class<?> serviceClass,Context context) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return false;
            }
        }
        return true;
    }
}
