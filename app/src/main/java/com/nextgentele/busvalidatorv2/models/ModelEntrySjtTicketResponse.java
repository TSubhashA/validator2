
package com.nextgentele.busvalidatorv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelEntrySjtTicketResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("payload")
    @Expose
    private ModelEntrySjtTicketResponsePayload payload;

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

    public ModelEntrySjtTicketResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(ModelEntrySjtTicketResponsePayload payload) {
        this.payload = payload;
    }

}
