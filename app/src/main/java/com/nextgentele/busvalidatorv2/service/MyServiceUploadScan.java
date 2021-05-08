package com.nextgentele.busvalidatorv2.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.nextgentele.busvalidatorv2.apiupdate.ApiClient;
import com.nextgentele.busvalidatorv2.apiupdate.ApiInterface;
import com.nextgentele.busvalidatorv2.models.ModelEntrySjtTicket;
import com.nextgentele.busvalidatorv2.models.ModelEntrySjtTicketResponse;
import com.nextgentele.busvalidatorv2.models.ModelExitSjtTicket;
import com.nextgentele.busvalidatorv2.roomdb.AppDatabase;
import com.nextgentele.busvalidatorv2.util.ConnectionDetector;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyServiceUploadScan extends Service {

    private final int m_interval = 90 * 1000;
    private Handler m_handler;
    ConnectionDetector connectionDetector;
    private AppDatabase appDatabase;
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


    public MyServiceUploadScan() {
    }

    @Override
    public void onCreate() {
        Log.w("MyServiceUploadScan", "onCreate callback called");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w("MyServiceUploadScan", "onStartCommand callback called");
        appDatabase = AppDatabase.getAppDatabase(MyServiceUploadScan.this);
        m_handler = new Handler();
        connectionDetector = new ConnectionDetector(this);
        startRepeatingTask();

        return START_STICKY;
    }

    Runnable m_statusChecker = new Runnable() {
        @Override
        public void run() {
            if (connectionDetector.isConnectingToInternet()) {
                Log.w("Upload Service", "Called");
                appDatabase.entrySjtDao().getEntry()
                        .subscribeOn(Schedulers.io())
                        .subscribe(new DisposableMaybeObserver<ModelEntrySjtTicket>() {
                            @Override
                            public void onSuccess(@NonNull ModelEntrySjtTicket modelEntrySjtTicket) {
                                if (modelEntrySjtTicket != null)
                                    updateEntry(modelEntrySjtTicket);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                            }
                        });

                appDatabase.exitSjtDao().getExit()
                        .subscribeOn(Schedulers.io())
                        .subscribe(new DisposableMaybeObserver<ModelExitSjtTicket>() {
                            @Override
                            public void onSuccess(@NonNull ModelExitSjtTicket modelExitSjtTicket) {
                                if (modelExitSjtTicket != null)
                                    updateExit(modelExitSjtTicket);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }

            //this function can change value of m_interval.
            m_handler.postDelayed(m_statusChecker, m_interval);
        }
    };

    public void updateEntry(ModelEntrySjtTicket modelEntrySjtTicket) {
        Log.w("Sevice", "Service Running");

        apiInterface.sjtEntry(modelEntrySjtTicket)
                .enqueue(new Callback<ModelEntrySjtTicketResponse>() {
                    @Override
                    public void onResponse(Call<ModelEntrySjtTicketResponse> call, Response<ModelEntrySjtTicketResponse> response) {
                        if (response.isSuccessful())
                            if (response.body().getStatus() == 0)
                                appDatabase.entrySjtDao().deleteEntry(modelEntrySjtTicket)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe();

                    }

                    @Override
                    public void onFailure(Call<ModelEntrySjtTicketResponse> call, Throwable t) {

                    }
                });
    }

    public void updateExit(ModelExitSjtTicket modelExitSjtTicket) {

        Log.w("Sevice", "Service Running");

        apiInterface.sjtExit(modelExitSjtTicket)
                .enqueue(new Callback<ModelEntrySjtTicketResponse>() {
                    @Override
                    public void onResponse(Call<ModelEntrySjtTicketResponse> call, Response<ModelEntrySjtTicketResponse> response) {
                        if (response.isSuccessful())
                            if (response.body().getStatus() == 0)
                                appDatabase.exitSjtDao().deleteExit(modelExitSjtTicket)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe();

                    }

                    @Override
                    public void onFailure(Call<ModelEntrySjtTicketResponse> call, Throwable t) {

                    }
                });
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

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}