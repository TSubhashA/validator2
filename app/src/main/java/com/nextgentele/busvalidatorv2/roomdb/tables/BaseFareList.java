package com.nextgentele.busvalidatorv2.roomdb.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "baseFare")
public class BaseFareList {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @SerializedName("backendKey_dest_stop")
    @Expose
    private String backendKeyDestStop;
    @SerializedName("backendKey_src_stop")
    @Expose
    private String backendKeySrcStop;
    @SerializedName("src_stop_txt")
    @Expose
    private String srcStopTxt;
    @SerializedName("fare_value")
    @Expose
    private String fareValue;
    @SerializedName("backendKey_fare")
    @Expose
    private String backendKeyFare;
    @SerializedName("dest_stop_txt")
    @Expose
    private String destStopTxt;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    public String getBackendKeyDestStop() {
        return backendKeyDestStop;
    }

    public void setBackendKeyDestStop(String backendKeyDestStop) {
        this.backendKeyDestStop = backendKeyDestStop;
    }

    public String getBackendKeySrcStop() {
        return backendKeySrcStop;
    }

    public void setBackendKeySrcStop(String backendKeySrcStop) {
        this.backendKeySrcStop = backendKeySrcStop;
    }

    public String getSrcStopTxt() {
        return srcStopTxt;
    }

    public void setSrcStopTxt(String srcStopTxt) {
        this.srcStopTxt = srcStopTxt;
    }

    public String getFareValue() {
        return fareValue;
    }

    public void setFareValue(String fareValue) {
        this.fareValue = fareValue;
    }

    public String getBackendKeyFare() {
        return backendKeyFare;
    }

    public void setBackendKeyFare(String backendKeyFare) {
        this.backendKeyFare = backendKeyFare;
    }

    public String getDestStopTxt() {
        return destStopTxt;
    }

    public void setDestStopTxt(String destStopTxt) {
        this.destStopTxt = destStopTxt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
