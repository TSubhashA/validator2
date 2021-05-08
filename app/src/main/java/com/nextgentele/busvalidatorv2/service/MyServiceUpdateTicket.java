package com.nextgentele.busvalidatorv2.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


import com.nextgentele.busvalidatorv2.R;
import com.nextgentele.busvalidatorv2.apiupdate.ApiClient;
import com.nextgentele.busvalidatorv2.apiupdate.ApiInterface;
import com.nextgentele.busvalidatorv2.constants.Constants;
import com.nextgentele.busvalidatorv2.models.ModelListActiveSjtTicket;
import com.nextgentele.busvalidatorv2.models.ModelListActiveSjtTicketPayload;
import com.nextgentele.busvalidatorv2.models.ModelListBaseFareResponse;
import com.nextgentele.busvalidatorv2.models.ModelListBaseFareResponsePayload;
import com.nextgentele.busvalidatorv2.models.ModelListRouteInfo;
import com.nextgentele.busvalidatorv2.pref.AppPreferences;
import com.nextgentele.busvalidatorv2.pref.VariablesConstant;
import com.nextgentele.busvalidatorv2.roomdb.AppDatabase;
import com.nextgentele.busvalidatorv2.roomdb.tables.BaseFareList;
import com.nextgentele.busvalidatorv2.util.ConnectionDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyServiceUpdateTicket extends Service {
    private final int m_interval =  5 * 1000;
    private Handler m_handler;
    AppDatabase appDatabase;
    ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
    ConnectionDetector connectionDetector;
    public MyServiceUpdateTicket() {
    }

    @Override
    public void onCreate() {
        Log.w("MyService", "onCreate callback called");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w("MyService", "onStartCommand callback called");
        m_handler = new Handler();
        connectionDetector=new ConnectionDetector(this);
       startRepeatingTask();
        return START_STICKY;
    }

    Runnable m_statusChecker = new Runnable() {
        @Override
        public void run() {
            if (connectionDetector.isConnectingToInternet())
            runner(); //this function can change value of m_interval.
            m_handler.postDelayed(m_statusChecker, m_interval);
        }
    };

    private void runner() {
      Log.w("Sevice","Service Running");
      appDatabase = AppDatabase.getAppDatabase(this);

        ModelListRouteInfo modelListRouteInfo = new ModelListRouteInfo();
        modelListRouteInfo.setClientID(getResources().getString(R.string.client_id));
        modelListRouteInfo.setBackendKeyRoute(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_route, this));

        apiInterface.getSJTList(modelListRouteInfo)
                .enqueue(new Callback<ModelListActiveSjtTicket>() {
                    @Override
                    public void onResponse(Call<ModelListActiveSjtTicket> call, Response<ModelListActiveSjtTicket> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 0) {
                                updateTicketListDb(response.body().getPayload());
                            }

                        }
                    }
                    @Override
                    public void onFailure(Call<ModelListActiveSjtTicket> call, Throwable t) {

                    }
                });
    }

    public void updateTicketListDb(List<ModelListActiveSjtTicketPayload> ticketList) {
        appDatabase.sjtTicketListDao().addTickets(ticketList)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void startRepeatingTask() {
        m_statusChecker.run();
    }

    public void stopRepeatingTask() {
        m_handler.removeCallbacks(m_statusChecker);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
        Log.w("MyService", "onDestroy callback called");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}