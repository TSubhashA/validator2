
package com.nextgentele.busvalidatorv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelValidatorInfo {

    @SerializedName("clientID")
    @Expose
    private String clientID;
    @SerializedName("validatorId")
    @Expose
    private String validatorId;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getValidatorId() {
        return validatorId;
    }

    public void setValidatorId(String validatorId) {
        this.validatorId = validatorId;
    }

}
