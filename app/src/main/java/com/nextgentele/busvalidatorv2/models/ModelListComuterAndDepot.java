
package com.nextgentele.busvalidatorv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelListComuterAndDepot {

    @SerializedName("clientID")
    @Expose
    private String clientID;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

}
