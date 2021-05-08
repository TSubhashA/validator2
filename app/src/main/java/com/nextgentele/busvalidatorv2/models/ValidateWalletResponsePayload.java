
package com.nextgentele.busvalidatorv2.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ValidateWalletResponsePayload {

    @SerializedName("fare_debited")
    @Expose
    private String fareDebited;
    @SerializedName("wallet_balance")
    @Expose
    private String walletBalance;

    public String getFareDebited() {
        return fareDebited;
    }

    public void setFareDebited(String fareDebited) {
        this.fareDebited = fareDebited;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

}
