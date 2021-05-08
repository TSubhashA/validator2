
package com.nextgentele.busvalidatorv2.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ValidateWallet {

    @SerializedName("clientID")
    @Expose
    private String clientID;
    @SerializedName("wallet_qrcode")
    @Expose
    private String walletQrcode;
    @SerializedName("entry_stop_backendKey")
    @Expose
    private String entryStopBackendKey;
    @SerializedName("trip_backendKey")
    @Expose
    private String tripBackendKey;
    @SerializedName("imei")
    @Expose
    private String imei;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getWalletQrcode() {
        return walletQrcode;
    }

    public void setWalletQrcode(String walletQrcode) {
        this.walletQrcode = walletQrcode;
    }

    public String getEntryStopBackendKey() {
        return entryStopBackendKey;
    }

    public void setEntryStopBackendKey(String entryStopBackendKey) {
        this.entryStopBackendKey = entryStopBackendKey;
    }

    public String getTripBackendKey() {
        return tripBackendKey;
    }

    public void setTripBackendKey(String tripBackendKey) {
        this.tripBackendKey = tripBackendKey;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }


}
