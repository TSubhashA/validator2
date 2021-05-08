
package com.nextgentele.busvalidatorv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelTripStart {

    @SerializedName("clientID")
    @Expose
    private String clientID;

    @SerializedName("backendKey_bus")
    @Expose
    private String backendKeyBus;
    @SerializedName("backendKey_route")
    @Expose
    private String backendKeyRoute;
    @SerializedName("backendKey_driver")
    @Expose
    private String backendKeyDriver;
    @SerializedName("tripStart_datetime")
    @Expose
    private String tripStartDatetime;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getBackendKeyBus() {
        return backendKeyBus;
    }

    public void setBackendKeyBus(String backendKeyBus) {
        this.backendKeyBus = backendKeyBus;
    }

    public String getBackendKeyRoute() {
        return backendKeyRoute;
    }

    public void setBackendKeyRoute(String backendKeyRoute) {
        this.backendKeyRoute = backendKeyRoute;
    }

    public String getBackendKeyDriver() {
        return backendKeyDriver;
    }

    public void setBackendKeyDriver(String backendKeyDriver) {
        this.backendKeyDriver = backendKeyDriver;
    }

    public String getTripStartDatetime() {
        return tripStartDatetime;
    }

    public void setTripStartDatetime(String tripStartDatetime) {
        this.tripStartDatetime = tripStartDatetime;
    }

}
