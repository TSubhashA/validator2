package com.nextgentele.busvalidatorv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDriverLogin {

    @SerializedName("clientID")
    @Expose
    private String clientID;
    @SerializedName("entrycode")
    @Expose
    private String entrycode;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getEntrycode() {
        return entrycode;
    }

    public void setEntrycode(String entrycode) {
        this.entrycode = entrycode;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
