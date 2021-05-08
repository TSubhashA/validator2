
package com.nextgentele.busvalidatorv2.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "exitSjtTicket")
public class ModelExitSjtTicket {

    @SerializedName("clientID")
    @Expose
    private String clientID;
    @SerializedName("sjt_qrcode")
    @Expose
    @PrimaryKey
    @NonNull
    private String sjtQrcode;
    @SerializedName("exit_stop_backendKey")
    @Expose
    private String exitStopBackendKey;
    @SerializedName("trip_backendKey")
    @Expose
    private String tripBackendKey;
    @SerializedName("exit_datetime")
    @Expose
    private String exitDatetime;
    @SerializedName("imei")
    @Expose
    private String imei;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getSjtQrcode() {
        return sjtQrcode;
    }

    public void setSjtQrcode(String sjtQrcode) {
        this.sjtQrcode = sjtQrcode;
    }

    public String getExitStopBackendKey() {
        return exitStopBackendKey;
    }

    public void setExitStopBackendKey(String exitStopBackendKey) {
        this.exitStopBackendKey = exitStopBackendKey;
    }

    public String getTripBackendKey() {
        return tripBackendKey;
    }

    public void setTripBackendKey(String tripBackendKey) {
        this.tripBackendKey = tripBackendKey;
    }

    public String getExitDatetime() {
        return exitDatetime;
    }

    public void setExitDatetime(String exitDatetime) {
        this.exitDatetime = exitDatetime;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

}
