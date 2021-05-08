package com.nextgentele.busvalidatorv2.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.nextgentele.busvalidatorv2.R;
import com.nextgentele.busvalidatorv2.apiupdate.ApiClient;
import com.nextgentele.busvalidatorv2.apiupdate.ApiInterface;
import com.nextgentele.busvalidatorv2.models.ModelConfigurationResponse;
import com.nextgentele.busvalidatorv2.models.ModelListActiveSjtTicket;
import com.nextgentele.busvalidatorv2.models.ModelListActiveSjtTicketPayload;
import com.nextgentele.busvalidatorv2.models.ModelListBaseFareResponse;
import com.nextgentele.busvalidatorv2.models.ModelListBaseFareResponsePayload;
import com.nextgentele.busvalidatorv2.models.ModelListRouteInfo;
import com.nextgentele.busvalidatorv2.pref.AppPreferences;
import com.nextgentele.busvalidatorv2.pref.VariablesConstant;
import com.nextgentele.busvalidatorv2.roomdb.AppDatabase;
import com.nextgentele.busvalidatorv2.roomdb.tables.BaseFareList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CheckResult")
public class MasterManager {

    Context context;

    ApiInterface apiInterface;
    AppDatabase appDatabase;

    public MasterManager(Context context) {
        this.context = context;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        appDatabase = AppDatabase.getAppDatabase(context);
        getFares();
    }

    public void getFares() {
        ModelListRouteInfo modelListRouteInfo = new ModelListRouteInfo();
        modelListRouteInfo.setClientID(context.getResources().getString(R.string.client_id));
        modelListRouteInfo.setBackendKeyRoute(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_route, context));

        apiInterface.getListBaseFares(modelListRouteInfo)
                .enqueue(new Callback<ModelListBaseFareResponse>() {
                    @Override
                    public void onResponse(Call<ModelListBaseFareResponse> call, Response<ModelListBaseFareResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 0) {
                                List<BaseFareList> lists = new ArrayList<>();
                                for (ModelListBaseFareResponsePayload modelListBaseFareResponsePayload : response.body().getPayload()) {
                                    BaseFareList baseFareList = new BaseFareList();
                                    baseFareList.setIsActive(modelListBaseFareResponsePayload.getIsActive());
                                    baseFareList.setBackendKeyDestStop(modelListBaseFareResponsePayload.getBackendKeyDestStop());
                                    baseFareList.setBackendKeyFare(modelListBaseFareResponsePayload.getBackendKeyFare());
                                    baseFareList.setBackendKeySrcStop(modelListBaseFareResponsePayload.getBackendKeySrcStop());
                                    baseFareList.setDestStopTxt(modelListBaseFareResponsePayload.getDestStopTxt());
                                    baseFareList.setFareValue(modelListBaseFareResponsePayload.getFareValue());
                                    baseFareList.setSrcStopTxt(modelListBaseFareResponsePayload.getSrcStopTxt());
                                    lists.add(baseFareList);
                                }
                                fareUpdate(lists);
                                getConfiguration();
                            } else getFares();

                        }
                    }

                    @Override
                    public void onFailure(Call<ModelListBaseFareResponse> call, Throwable t) {
                        getFares();
                    }
                });

    }

    public void fareUpdate(List<BaseFareList> lists) {

        appDatabase.baseFareListDao().addBaseFareList(lists)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void getConfiguration() {
        ModelListRouteInfo modelListRouteInfo = new ModelListRouteInfo();
        modelListRouteInfo.setBackendKeyRoute(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_route, context));
        modelListRouteInfo.setClientID(context.getResources().getString(R.string.client_id));

        apiInterface.getConfiguration(modelListRouteInfo)
                .enqueue(new Callback<ModelConfigurationResponse>() {
                    @Override
                    public void onResponse(Call<ModelConfigurationResponse> call, Response<ModelConfigurationResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 0) {
                                AppPreferences.setAppPrefrences(VariablesConstant.AllowPostEntry, response.body().getPayload().getAllowPostEntry(), context);
                                AppPreferences.setAppPrefrences(VariablesConstant.AllowPreExit, response.body().getPayload().getAllowPreExit(), context);
                                getTicketList();
                            } else
                                getConfiguration();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelConfigurationResponse> call, Throwable t) {
                        getConfiguration();
                    }
                });
    }

    public void getTicketList() {

        ModelListRouteInfo modelListRouteInfo = new ModelListRouteInfo();
        modelListRouteInfo.setClientID(context.getString(R.string.client_id));
        modelListRouteInfo.setBackendKeyRoute(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_route, context));

        apiInterface.getSJTList(modelListRouteInfo)
                .enqueue(new Callback<ModelListActiveSjtTicket>() {
                    @Override
                    public void onResponse(Call<ModelListActiveSjtTicket> call, Response<ModelListActiveSjtTicket> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 0) {
                                updateTicketListDb(response.body().getPayload());
                            } else
                                getTicketList();
                        }
                    }
                    @Override
                    public void onFailure(Call<ModelListActiveSjtTicket> call, Throwable t) {
                        getTicketList();
                    }
                });
    }

    public void updateTicketListDb(List<ModelListActiveSjtTicketPayload> ticketList) {
        appDatabase.sjtTicketListDao().addTickets(ticketList)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
