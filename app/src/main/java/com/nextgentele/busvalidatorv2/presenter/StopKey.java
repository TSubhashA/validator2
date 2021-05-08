package com.nextgentele.busvalidatorv2.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.nextgentele.busvalidatorv2.roomdb.AppDatabase;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class StopKey {

    @SuppressLint("CheckResult")
    public static String getCurrentStopKey(Context context, int index){
        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
        final String[] key = {""};
        appDatabase.stopsDao().getStopKey(index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(backendKey -> {
                    key[0] =backendKey;
                }, error -> {
                    Log.e(TAG, " onDataSetError");
                });

        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return key[0];
    }
}
