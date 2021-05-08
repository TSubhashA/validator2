package com.nextgentele.busvalidatorv2.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class ModelDriverLoginResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("payload")
    @Expose
    @Nullable
    private ModelDriverLoginResponsePayload payload;


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

    public ModelDriverLoginResponsePayload getPayload() {
        return payload;
    }

    public void setPayload(ModelDriverLoginResponsePayload payload) {
        this.payload = payload;
    }

}
