package com.nextgentele.busvalidatorv2.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.nextgentele.busvalidatorv2.R;
import com.nextgentele.busvalidatorv2.apiupdate.ApiClient;
import com.nextgentele.busvalidatorv2.apiupdate.ApiInterface;
import com.nextgentele.busvalidatorv2.callback.LoginCallBack;
import com.nextgentele.busvalidatorv2.constants.Constants;
import com.nextgentele.busvalidatorv2.models.ModelDriverLogin;
import com.nextgentele.busvalidatorv2.models.ModelDriverLoginResponse;
import com.nextgentele.busvalidatorv2.models.ModelListComuterAndDepot;
import com.nextgentele.busvalidatorv2.models.ModelListDepotResponse;
import com.nextgentele.busvalidatorv2.models.ModelListDepotResponsePayload;
import com.nextgentele.busvalidatorv2.models.ModelValidatorInfo;
import com.nextgentele.busvalidatorv2.models.ModelValidatorInfoResponse;
import com.nextgentele.busvalidatorv2.pref.AppPreferences;
import com.nextgentele.busvalidatorv2.pref.VariablesConstant;
import com.nextgentele.busvalidatorv2.roomdb.AppDatabase;
import com.nextgentele.busvalidatorv2.util.ConnectionDetector;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginManager {

    private static final String TAG = LoginManager.class.getSimpleName();
    Context context;
    LoginCallBack loginCallBack;
    ApiInterface apiInterface;
    AppDatabase appDatabase;

    @SuppressLint("CheckResult")
    public LoginManager(Context context) {
        this.context = context;
        this.loginCallBack = (LoginCallBack) context;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        appDatabase = AppDatabase.getAppDatabase(context);
        clearAlltable();
    }

    public void Login(String mobile, String password) {
        ModelDriverLogin modelDriverLogin = new ModelDriverLogin();
        modelDriverLogin.setClientID("loginCallBack.getResources().getString(R.string.client_id)");
        modelDriverLogin.setImei(Constants.imei);
        modelDriverLogin.setEntrycode(password);
        modelDriverLogin.setMobile(mobile);

       Call<ModelDriverLoginResponse> call= apiInterface.login(modelDriverLogin);
                call.enqueue(new Callback<ModelDriverLoginResponse>() {
                    @Override
                    public void onResponse(Call<ModelDriverLoginResponse> call, Response<ModelDriverLoginResponse> response) {
                        if (response.code()==200){
                            if (response.body().getStatus() == 0) {
                                AppPreferences.setAppPrefrences(VariablesConstant.backendKey_driver, response.body().getPayload().getBackendKey(), context);
                                getDepot();
//                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG," loginNot : "+response.body().getMessage() );
                                loginCallBack.onError(response.body().getMessage());}
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelDriverLoginResponse> call, Throwable t) {
                        Log.e(TAG," loginOnFailed : "+t.getMessage() + " : "+t.getLocalizedMessage() );
//                        loginCallBack.onError(String.valueOf(t.getCause()));
                        Login(mobile,password);
                    }
                });

    }

    void getDepot() {
        ModelListComuterAndDepot modelListComuterAndDepot = new ModelListComuterAndDepot();
        modelListComuterAndDepot.setClientID(context.getResources().getString(R.string.client_id));

        Call<ModelListDepotResponse> call=apiInterface.getDepots(modelListComuterAndDepot);
                call.enqueue(new Callback<ModelListDepotResponse>() {
                    @Override
                    public void onResponse(Call<ModelListDepotResponse> call, Response<ModelListDepotResponse> response) {
                        if (response.code() == 200) {
                            if (response.body().getStatus() == 0) {
                                updateDepotDb(response.body().getPayload());
                            } else {
                                Log.e(TAG, " getDepotNot : " + response.body().getMessage());
                                loginCallBack.onError(response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelListDepotResponse> call, Throwable t) {
                        Log.e(TAG," getDepotOnFailed : "+t.getMessage() );
//                        loginCallBack.onError(String.valueOf(t.getCause()));
                        getDepot();
                    }
                });

//
    }


    void updateDepotDb(List<ModelListDepotResponsePayload> modelListDepotResponsePayloads) {

        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);

        appDatabase.depotListDao().addDepotList(modelListDepotResponsePayloads)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        getValidatorInfo();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG," updatDbonError : "+e.getMessage() );
                        loginCallBack.onError(String.valueOf(e.getCause()));
                    }
                });
//                new DisposableSingleObserver<List<Long>>() {
//                    @Override
//                    public void onSuccess(@NonNull List<Long> longs) {
//                        getValidatorInfo();
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.e(TAG," updatDbonError : "+e.getMessage() );
//                        loginCallBack.onError(String.valueOf(e.getCause()));
//                    }
//                });

    }

    void getValidatorInfo(){

        ModelValidatorInfo  modelValidatorInfo=new ModelValidatorInfo();
        modelValidatorInfo.setClientID(context.getString(R.string.client_id));
        modelValidatorInfo.setValidatorId(ConnectionDetector.getMacAddr());

        Call<ModelValidatorInfoResponse> call=apiInterface.getValidatorInfo(modelValidatorInfo);
               call .enqueue(new Callback<ModelValidatorInfoResponse>() {
                    @Override
                    public void onResponse(Call<ModelValidatorInfoResponse> call, Response<ModelValidatorInfoResponse> response) {
                        if (response.code()==200){
                            if (response.body().getStatus()==0){
                            ModelValidatorInfoResponse modelValidatorInfoResponse=response.body();
                                AppPreferences.setAppPrefrences(VariablesConstant.backendKey_bus,modelValidatorInfoResponse.getPayload().getBackendKeyBus(),context);
                                AppPreferences.setAppPrefrences(VariablesConstant.BUS_No,modelValidatorInfoResponse.getPayload().getBusIdentifier(),context);
                           loginCallBack.onLoginSuccess();
                            } else {
                                Log.e(TAG," validatorInfoNot : "+response.body().getMessage() );
                                loginCallBack.onError(response.body().getMessage());}
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelValidatorInfoResponse> call, Throwable t) {
                        Log.e(TAG," validatorInfoOnFailed : "+t.getMessage() +" : "+ t.getLocalizedMessage());
//                        loginCallBack.onError(String.valueOf(t.getCause()));
                        getValidatorInfo();
                    }
                });
    }


    private void clearAlltable(){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                appDatabase.clearAllTables();
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "--- clearAllTables(): run() ---");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "--- clearAllTables(): accept(Throwable throwable) ----");
                        Log.d(TAG, "throwable.getMessage(): "+throwable.getMessage());


                    }
                });
    }




}
