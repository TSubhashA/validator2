
package com.nextgentele.busvalidatorv2.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelListDepotResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("payload")
    @Expose
    private List<ModelListDepotResponsePayload> payload = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelListDepotResponsePayload> getPayload() {
        return payload;
    }

    public void setPayload(List<ModelListDepotResponsePayload> payload) {
        this.payload = payload;
    }

}
