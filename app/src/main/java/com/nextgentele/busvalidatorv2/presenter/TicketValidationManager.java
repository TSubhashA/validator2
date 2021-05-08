package com.nextgentele.busvalidatorv2.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.nextgentele.busvalidatorv2.R;
import com.nextgentele.busvalidatorv2.activity.MainActivity;
import com.nextgentele.busvalidatorv2.apiupdate.ApiClient;
import com.nextgentele.busvalidatorv2.apiupdate.ApiInterface;
import com.nextgentele.busvalidatorv2.models.ModelListActiveSjtTicketPayload;
import com.nextgentele.busvalidatorv2.models.ValidateWallet;
import com.nextgentele.busvalidatorv2.models.ValidateWalletResponse;
import com.nextgentele.busvalidatorv2.pref.AppPreferences;
import com.nextgentele.busvalidatorv2.pref.VariablesConstant;
import com.nextgentele.busvalidatorv2.roomdb.AppDatabase;
import com.nextgentele.busvalidatorv2.scannerqr.TicketValidation;
import com.nextgentele.busvalidatorv2.util.PermissionManagerUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CheckResult")
public class TicketValidationManager extends MainActivity {
    private static final String TAG = TicketValidationManager.class.getSimpleName();
    AppDatabase appDatabase;
    TicketValidation context;
    ApiInterface apiInterface;
    PermissionManagerUtil pm;


    public TicketValidationManager(TicketValidation context) {
        this.context = context;
        appDatabase = AppDatabase.getAppDatabase(context);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        pm = new PermissionManagerUtil(context);
    }

    public ModelListActiveSjtTicketPayload sjtTicket(String qr) {
        final ModelListActiveSjtTicketPayload[] model = {null};

        appDatabase.sjtTicketListDao().getSjtTicket(qr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(modelListActiveSjtTicketPayload -> {
                    model[0] = modelListActiveSjtTicketPayload;
                    Log.e(TAG, " dbReturn : " + model[0]);
                }, error -> {
                    Log.e(TAG, " onDataSetError");
                });
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.e(TAG, " onReturn value : " + model[0]);
        return model[0];
    }

    public ModelListActiveSjtTicketPayload rjtTicket(String qr) {
        ModelListActiveSjtTicketPayload[] model = {null};
        appDatabase.sjtTicketListDao().getRjtTicket(qr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(modelListActiveSjtTicketPayload -> {
                    model[0] = modelListActiveSjtTicketPayload;
                }, error -> {
                    Log.e(TAG, " onDataSetError");
                });
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return model[0];
    }

    public Integer getStopOrder(String stopname) {
        int[] index = new int[1];

        appDatabase.stopsDao().getStoporder(stopname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    index[0] = integer;
                }, error -> {
                    Log.e(TAG, " onDataSetError");
                });

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return index[0];

    }

    public void updateTicket(ModelListActiveSjtTicketPayload ticket) {
        appDatabase.sjtTicketListDao().updateTickets(ticket)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void validateWallet(String qrHash) {

        ValidateWallet valid = new ValidateWallet();
        valid.setClientID(context.getString(R.string.client_id));
        valid.setImei(pm.getDeviceId());
        valid.setTripBackendKey(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_trip, context));
        valid.setWalletQrcode(qrHash);
        valid.setEntryStopBackendKey(AppPreferences.getAppPrefrences(VariablesConstant.CURRENT_STOP_KEY, context));

        apiInterface.validateWallet(valid)
                .enqueue(new Callback<ValidateWalletResponse>() {
                    @Override
                    public void onResponse(Call<ValidateWalletResponse> call, Response<ValidateWalletResponse> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            if (response.body().getStatus() == 0) {
                                if (response.body().getMessage().equals("Entry Allowed"))
                                    context.updateValletQR(1,"",response.body().getPayload().getWalletBalance(),"0");
                                else
                                    context.updateValletQR(2,"",response.body().getPayload().getWalletBalance(),response.body().getPayload().getFareDebited());

                            } else
                                context.updateValletQR(0,String.valueOf(response.body().getPayload()),"0","0");
                        }

                    }

                    @Override
                    public void onFailure(Call<ValidateWalletResponse> call, Throwable t) {

                    }
                });


    }


}
