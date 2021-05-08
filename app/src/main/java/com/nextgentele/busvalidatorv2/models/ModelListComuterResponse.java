
package com.nextgentele.busvalidatorv2.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelListComuterResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("payload")
    @Expose
    private List<ModelListComuterResponsePayload> payload = null;

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

    public List<ModelListComuterResponsePayload> getPayload() {
        return payload;
    }

    public void setPayload(List<ModelListComuterResponsePayload> payload) {
        this.payload = payload;
    }

}
