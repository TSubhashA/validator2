
package com.nextgentele.busvalidatorv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelConfigurationResponsePayload {

    @SerializedName("AllowPostEntry")
    @Expose
    private String allowPostEntry;
    @SerializedName("AllowPreExit")
    @Expose
    private String allowPreExit;
    @SerializedName("DateTime")
    @Expose
    private String dateTime;

    public String getAllowPostEntry() {
        return allowPostEntry;
    }

    public void setAllowPostEntry(String allowPostEntry) {
        this.allowPostEntry = allowPostEntry;
    }

    public String getAllowPreExit() {
        return allowPreExit;
    }

    public void setAllowPreExit(String allowPreExit) {
        this.allowPreExit = allowPreExit;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
