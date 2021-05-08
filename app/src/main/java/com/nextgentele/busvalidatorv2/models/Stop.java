
package com.nextgentele.busvalidatorv2.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "stops")
public class Stop {

    @SerializedName("numeric_identifier")
    @Expose
    private String numericIdentifier;
    @SerializedName("stop_category")
    @Expose
    private String stopCategory;
    @SerializedName("gps_latitude")
    @Expose
    private String gpsLatitude;
    @SerializedName("gps_longitude")
    @Expose
    private String gpsLongitude;
    @SerializedName("backendKey")
    @Expose
    private String backendKey;
    @SerializedName("stop_order")
    @Expose
    @PrimaryKey
    private Integer stopOrder;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("textual_Identifier")
    @Expose
    private String textualIdentifier;

    public String getNumericIdentifier() {
        return numericIdentifier;
    }

    public void setNumericIdentifier(String numericIdentifier) {
        this.numericIdentifier = numericIdentifier;
    }

    public String getStopCategory() {
        return stopCategory;
    }

    public void setStopCategory(String stopCategory) {
        this.stopCategory = stopCategory;
    }

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public String getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(String gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public String getBackendKey() {
        return backendKey;
    }

    public void setBackendKey(String backendKey) {
        this.backendKey = backendKey;
    }

    public Integer getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(Integer stopOrder) {
        this.stopOrder = stopOrder;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getTextualIdentifier() {
        return textualIdentifier;
    }

    public void setTextualIdentifier(String textualIdentifier) {
        this.textualIdentifier = textualIdentifier;
    }

}
