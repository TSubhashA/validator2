package com.nextgentele.busvalidatorv2.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckPermission {

    Context context;


    public CheckPermission(Context context) {
        this.context = context;
    }

    public void checkPermission(int requestCode, String[] permissions)
    {List<String> Permissions1=new ArrayList<>();
        for (String permission:permissions) {
            if (ContextCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_DENIED) {
                Permissions1.add(permission);
            }
        }
        // Requesting the permission
        ActivityCompat.requestPermissions((Activity) context,
                Permissions1.toArray(new String[Permissions1.size()]),
                requestCode);

    }

    }







