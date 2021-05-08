package com.nextgentele.busvalidatorv2.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.nextgentele.busvalidatorv2.R;
import com.nextgentele.busvalidatorv2.apiupdate.ApiClient;
import com.nextgentele.busvalidatorv2.apiupdate.ApiInterface;
import com.nextgentele.busvalidatorv2.callback.RouteCallBack;
import com.nextgentele.busvalidatorv2.models.ModelListRoute;
import com.nextgentele.busvalidatorv2.models.ModelListRouteInfo;
import com.nextgentele.busvalidatorv2.models.ModelListRouteInfoResponse;
import com.nextgentele.busvalidatorv2.models.ModelListRouteResponse;
import com.nextgentele.busvalidatorv2.models.ModelListRouteResponsePayload;
import com.nextgentele.busvalidatorv2.models.ModelTripStart;
import com.nextgentele.busvalidatorv2.models.ModelTripStartResponse;
import com.nextgentele.busvalidatorv2.models.Stop;
import com.nextgentele.busvalidatorv2.pref.AppPreferences;
import com.nextgentele.busvalidatorv2.pref.VariablesConstant;
import com.nextgentele.busvalidatorv2.roomdb.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CheckResult")
public class RouteManager {
    Context context;
    RouteCallBack routeCallBack;
    ApiInterface apiInterface;
    AppDatabase appDatabase;

    public RouteManager(Context context) {
        this.context = context;
        routeCallBack = (RouteCallBack) context;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        appDatabase = AppDatabase.getAppDatabase(context);
    }

    public List<String> getDepot() {

        List<String> depotList = new ArrayList<>();

        appDatabase.depotListDao().getDepotList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableMaybeObserver<List<String>>() {
                    @Override
                    public void onSuccess(@NonNull List<String> strings) {
                        depotList.addAll(strings);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return depotList;
    }

    public void getRoutes(String id) {

        appDatabase.depotListDao().getDepotBackend(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull String s) {
                        getRouteList(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        routeCallBack.onError(e.getLocalizedMessage());
                    }
                });

    }

    public void getRouteList(String key) {
        AppPreferences.setAppPrefrences(VariablesConstant.backendKey_origin_depot, key, context);
        ModelListRoute modelListRoute = new ModelListRoute();
        modelListRoute.setBackendKeyOriginDepot(key);
        modelListRoute.setClientID(context.getResources().getString(R.string.client_id));
        final HashMap<String, String> hashMap = new HashMap<>();

        apiInterface.getRoutes(modelListRoute)
                .enqueue(new Callback<ModelListRouteResponse>() {
                    @Override
                    public void onResponse(Call<ModelListRouteResponse> call, Response<ModelListRouteResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 0) {
                                for (ModelListRouteResponsePayload payload : response.body().getPayload()) {
                                    hashMap.put(payload.getNumericIdentifier(), payload.getBackendKeyRoute());
                                }
                                routeCallBack.setRouteSpinner(hashMap);
                            } else routeCallBack.onError(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelListRouteResponse> call, Throwable t) {
                       getRouteList(key);
                    }
                });


    }

    public void getRouteInfo(String backendKey_route) {
        AppPreferences.setAppPrefrences(VariablesConstant.backendKey_route, backendKey_route, context);
        ModelListRouteInfo modelListRoute = new ModelListRouteInfo();
        modelListRoute.setBackendKeyRoute(backendKey_route);
        modelListRoute.setClientID(context.getResources().getString(R.string.client_id));

        apiInterface.getRouteInfo(modelListRoute)
                .enqueue(new Callback<ModelListRouteInfoResponse>() {
                    @Override
                    public void onResponse(Call<ModelListRouteInfoResponse> call, Response<ModelListRouteInfoResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 0) {
                                addStops(response.body().getPayload().getStops());
                                AppPreferences.setAppPrefrences(VariablesConstant.Source_Destination, response.body().getPayload().getTextualIdentifier(), context);
startTrip();
                            } else
                                routeCallBack.onError(response.body().getMessage());
                        }else
                            getRouteInfo(backendKey_route);
                    }

                    @Override
                    public void onFailure(Call<ModelListRouteInfoResponse> call, Throwable t) {
                       getRouteInfo(backendKey_route);
                    }
                });


    }

    public void addStops(List<Stop> list) {
        appDatabase.stopsDao().addStopsList(list)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void startTrip() {

        ModelTripStart modelTripStart = new ModelTripStart();
        modelTripStart.setBackendKeyBus(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_bus, context));
        modelTripStart.setBackendKeyDriver(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_driver, context));
        modelTripStart.setBackendKeyRoute(AppPreferences.getAppPrefrences(VariablesConstant.backendKey_route, context));
        modelTripStart.setClientID(context.getString(R.string.client_id));

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyyyhhmmss");
        modelTripStart.setTripStartDatetime(String.valueOf(sdf.format(new Date())));

        apiInterface.tripStart(modelTripStart)
                .enqueue(new Callback<ModelTripStartResponse>() {
                    @Override
                    public void onResponse(Call<ModelTripStartResponse> call, Response<ModelTripStartResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 0) {
                                AppPreferences.setAppPrefrences(VariablesConstant.backendKey_trip, response.body().getPayload().getBackendKeyTrip(), context);
                                routeCallBack.onLoginSuccess();
                            } else
                                routeCallBack.onError(response.body().getMessage());
                        }else
                            startTrip();
                    }
                    @Override
                    public void onFailure(Call<ModelTripStartResponse> call, Throwable t) {
//                        routeCallBack.onError(String.valueOf(t.getCause()));
                        startTrip();
                    }
                });
    }

}
