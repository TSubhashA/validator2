
package com.nextgentele.busvalidatorv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelListRouteInfo {

    @SerializedName("clientID")
    @Expose
    private String clientID;
    @SerializedName("backendKey_route")
    @Expose
    private String backendKeyRoute;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getBackendKeyRoute() {
        return backendKeyRoute;
    }

    public void setBackendKeyRoute(String backendKeyRoute) {
        this.backendKeyRoute = backendKeyRoute;
    }

}
