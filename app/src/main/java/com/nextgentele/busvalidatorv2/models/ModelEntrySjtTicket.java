
package com.nextgentele.busvalidatorv2.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "entrySjtTicket")
public class ModelEntrySjtTicket {

    @SerializedName("clientID")
    @Expose
    private String clientID;
    @SerializedName("sjt_qrcode")
    @Expose
    @PrimaryKey
    @NonNull
    private String sjtQrcode;
    @SerializedName("entry_stop_backendKey")
    @Expose
    private String entryStopBackendKey;
    @SerializedName("trip_backendKey")
    @Expose
    private String tripBackendKey;
    @SerializedName("entry_datetime")
    @Expose
    private String entryDatetime;
    @SerializedName("imei")
    @Expose
    private String imei;

    public String getTripBackendKey() {
        return tripBackendKey;
    }

    public void setTripBackendKey(String tripBackendKey) {
        this.tripBackendKey = tripBackendKey;
    }

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

    public String getEntryStopBackendKey() {
        return entryStopBackendKey;
    }

    public void setEntryStopBackendKey(String entryStopBackendKey) {
        this.entryStopBackendKey = entryStopBackendKey;
    }

    public String getEntryDatetime() {
        return entryDatetime;
    }

    public void setEntryDatetime(String entryDatetime) {
        this.entryDatetime = entryDatetime;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

}
