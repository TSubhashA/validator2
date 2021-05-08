
package com.nextgentele.busvalidatorv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelListRoute {

    @SerializedName("clientID")
    @Expose
    private String clientID;
    @SerializedName("backendKey_origin_depot")
    @Expose
    private String backendKeyOriginDepot;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getBackendKeyOriginDepot() {
        return backendKeyOriginDepot;
    }

    public void setBackendKeyOriginDepot(String backendKeyOriginDepot) {
        this.backendKeyOriginDepot = backendKeyOriginDepot;
    }

}
