
package com.nextgentele.busvalidatorv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelValidatorInfoResponsePayload {

    @SerializedName("backendKey_bus")
    @Expose
    private String backendKeyBus;
    @SerializedName("bus_Identifier")
    @Expose
    private String busIdentifier;
    @SerializedName("validatorId")
    @Expose
    private String validatorId;

    public String getBackendKeyBus() {
        return backendKeyBus;
    }

    public void setBackendKeyBus(String backendKeyBus) {
        this.backendKeyBus = backendKeyBus;
    }

    public String getBusIdentifier() {
        return busIdentifier;
    }

    public void setBusIdentifier(String busIdentifier) {
        this.busIdentifier = busIdentifier;
    }

    public String getValidatorId() {
        return validatorId;
    }

    public void setValidatorId(String validatorId) {
        this.validatorId = validatorId;
    }

}
