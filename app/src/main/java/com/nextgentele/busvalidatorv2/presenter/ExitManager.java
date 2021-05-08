package com.nextgentele.busvalidatorv2.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.nextgentele.busvalidatorv2.R;
import com.nextgentele.busvalidatorv2.apiupdate.ApiClient;
import com.nextgentele.busvalidatorv2.apiupdate.ApiInterface;
import com.nextgentele.busvalidatorv2.models.ModelEntrySjtTicketResponse;
import com.nextgentele.busvalidatorv2.models.ModelExitSjtTicket;
import com.nextgentele.busvalidatorv2.models.ModelListActiveSjtTicketPayload;
import com.nextgentele.busvalidatorv2.pref.AppPreferences;
import com.nextgentele.busvalidatorv2.pref.VariablesConstant;
import com.nextgentele.busvalidatorv2.roomdb.AppDatabase;
import com.nextgentele.busvalidatorv2.util.PermissionManagerUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExitManager {
    private static final String TAG = ExitManager.class.getSimpleName();
    Context context;
    ApiInterface apiInterface;
    AppDatabase appDatabase;

    public ExitManager(Context context) {
        this.context = context;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        appDatabase = AppDatabase.getAppDatabase(context);
    }

    public void updateExit(ModelListActiveSjtTicketPayload model) {

        ModelExitSjtTicket modelExitSjtTicket = new ModelExitSjtTicket();
        modelExitSjtTicket.setClientID(context.getString(R.string.client_id));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
        modelExitSjtTicket.setExitDatetime(String.valueOf(sdf.format(new Date())));
        modelExitSjtTicket.setExitStopBackendKey(AppPreferences.getAppPrefrences(VariablesConstant.CURRENT_STOP_KEY,context));
        modelExitSjtTicket.setSjtQrcode(model.getSjtQrcode());
        modelExitSjtTicket.setTripBackendKey(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_trip, context));
        modelExitSjtTicket.setImei(new PermissionManagerUtil(context).getDeviceId());
        apiInterface.sjtExit(modelExitSjtTicket)
                .enqueue(new Callback<ModelEntrySjtTicketResponse>() {
                    @Override
                    public void onResponse(Call<ModelEntrySjtTicketResponse> call, Response<ModelEntrySjtTicketResponse> response) {
                        if (response.isSuccessful())
                            if (response.body().getStatus() == 0)
                                Log.e(TAG, " onSuccess");
                            else {
                                Log.e(TAG, " onFailed");
                                appDatabase.exitSjtDao().addExit(modelExitSjtTicket)
                                        .subscribeOn(Schedulers.io())
                                        .subscribe();
                            }
                    }

                    @Override
                    public void onFailure(Call<ModelEntrySjtTicketResponse> call, Throwable t) {
                        Log.e(TAG, " onFailed : " + t.getMessage());
                        appDatabase.exitSjtDao().addExit(modelExitSjtTicket)
                                .subscribeOn(Schedulers.io())
                                .subscribe();
                    }
                });

    }
}
