
package com.nextgentele.busvalidatorv2.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "listSjtTickets")
public class ModelListActiveSjtTicketPayload implements Serializable {

    @SerializedName("fare")
    @Expose
    private String fare;
    @SerializedName("entry_datetime")
    @Expose
    private String entryDatetime;
    @SerializedName("exit_available")
    @Expose
    private Boolean exitAvailable;
    @SerializedName("commuter_backendKey")
    @Expose
    private String commuterBackendKey;
    @SerializedName("sjt_qrcode")
    @Expose
    private String sjtQrcode;
    @SerializedName("rjt_expiring_on")
    @Expose
    private String rjtExpiringOn;
    @SerializedName("exit_datetime")
    @Expose
    private String exitDatetime;
    @SerializedName("rjt_booked")
    @Expose
    private Boolean rjtBooked;
    @SerializedName("exit_stop_backendKey")
    @Expose
    private String exitStopBackendKey;
    @SerializedName("entry_available")
    @Expose
    private Boolean entryAvailable;
    @SerializedName("src_stop_textual_Identifier")
    @Expose
    private String srcStopTextualIdentifier;
    @SerializedName("dest_stop_backendKey")
    @Expose
    private String destStopBackendKey;
    @SerializedName("entry_stop_textual_Identifier")
    @Expose
    private String entryStopTextualIdentifier;
    @SerializedName("is_expired")
    @Expose
    private Boolean isExpired;
    @SerializedName("exit_stop_textual_Identifier")
    @Expose
    private String exitStopTextualIdentifier;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("sjt_expiring_on")
    @Expose
    private String sjtExpiringOn;
    @SerializedName("src_stop_backendKey")
    @Expose
    private String srcStopBackendKey;
    @SerializedName("dest_stop_textual_Identifier")
    @Expose
    private String destStopTextualIdentifier;
    @SerializedName("last_updated_on")
    @Expose
    private String lastUpdatedOn;
    @SerializedName("rjt_qrcode")
    @Expose
    private String rjtQrcode;
    @SerializedName("entry_stop_backendKey")
    @Expose
    private String entryStopBackendKey;
    @SerializedName("sjt_backendKey")
    @Expose
    @PrimaryKey
    @NonNull
    private String sjtBackendKey;

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getEntryDatetime() {
        return entryDatetime;
    }

    public void setEntryDatetime(String entryDatetime) {
        this.entryDatetime = entryDatetime;
    }

    public Boolean getExitAvailable() {
        return exitAvailable;
    }

    public void setExitAvailable(Boolean exitAvailable) {
        this.exitAvailable = exitAvailable;
    }

    public String getCommuterBackendKey() {
        return commuterBackendKey;
    }

    public void setCommuterBackendKey(String commuterBackendKey) {
        this.commuterBackendKey = commuterBackendKey;
    }

    public String getSjtQrcode() {
        return sjtQrcode;
    }

    public void setSjtQrcode(String sjtQrcode) {
        this.sjtQrcode = sjtQrcode;
    }

    public String getRjtExpiringOn() {
        return rjtExpiringOn;
    }

    public void setRjtExpiringOn(String rjtExpiringOn) {
        this.rjtExpiringOn = rjtExpiringOn;
    }

    public String getExitDatetime() {
        return exitDatetime;
    }

    public void setExitDatetime(String exitDatetime) {
        this.exitDatetime = exitDatetime;
    }

    public Boolean getRjtBooked() {
        return rjtBooked;
    }

    public void setRjtBooked(Boolean rjtBooked) {
        this.rjtBooked = rjtBooked;
    }

    public String getExitStopBackendKey() {
        return exitStopBackendKey;
    }

    public void setExitStopBackendKey(String exitStopBackendKey) {
        this.exitStopBackendKey = exitStopBackendKey;
    }

    public Boolean getEntryAvailable() {
        return entryAvailable;
    }

    public void setEntryAvailable(Boolean entryAvailable) {
        this.entryAvailable = entryAvailable;
    }

    public String getSrcStopTextualIdentifier() {
        return srcStopTextualIdentifier;
    }

    public void setSrcStopTextualIdentifier(String srcStopTextualIdentifier) {
        this.srcStopTextualIdentifier = srcStopTextualIdentifier;
    }

    public String getDestStopBackendKey() {
        return destStopBackendKey;
    }

    public void setDestStopBackendKey(String destStopBackendKey) {
        this.destStopBackendKey = destStopBackendKey;
    }

    public String getEntryStopTextualIdentifier() {
        return entryStopTextualIdentifier;
    }

    public void setEntryStopTextualIdentifier(String entryStopTextualIdentifier) {
        this.entryStopTextualIdentifier = entryStopTextualIdentifier;
    }

    public Boolean getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public String getExitStopTextualIdentifier() {
        return exitStopTextualIdentifier;
    }

    public void setExitStopTextualIdentifier(String exitStopTextualIdentifier) {
        this.exitStopTextualIdentifier = exitStopTextualIdentifier;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getSjtExpiringOn() {
        return sjtExpiringOn;
    }

    public void setSjtExpiringOn(String sjtExpiringOn) {
        this.sjtExpiringOn = sjtExpiringOn;
    }

    public String getSrcStopBackendKey() {
        return srcStopBackendKey;
    }

    public void setSrcStopBackendKey(String srcStopBackendKey) {
        this.srcStopBackendKey = srcStopBackendKey;
    }

    public String getDestStopTextualIdentifier() {
        return destStopTextualIdentifier;
    }

    public void setDestStopTextualIdentifier(String destStopTextualIdentifier) {
        this.destStopTextualIdentifier = destStopTextualIdentifier;
    }

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getRjtQrcode() {
        return rjtQrcode;
    }

    public void setRjtQrcode(String rjtQrcode) {
        this.rjtQrcode = rjtQrcode;
    }

    public String getEntryStopBackendKey() {
        return entryStopBackendKey;
    }

    public void setEntryStopBackendKey(String entryStopBackendKey) {
        this.entryStopBackendKey = entryStopBackendKey;
    }

    public String getSjtBackendKey() {
        return sjtBackendKey;
    }

    public void setSjtBackendKey(String sjtBackendKey) {
        this.sjtBackendKey = sjtBackendKey;
    }

}
