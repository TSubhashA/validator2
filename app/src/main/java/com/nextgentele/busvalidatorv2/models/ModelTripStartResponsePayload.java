
package com.nextgentele.busvalidatorv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelTripStartResponsePayload {

    @SerializedName("backendKey_trip")
    @Expose
    private String backendKeyTrip;

    public String getBackendKeyTrip() {
        return backendKeyTrip;
    }

    public void setBackendKeyTrip(String backendKeyTrip) {
        this.backendKeyTrip = backendKeyTrip;
    }

}
