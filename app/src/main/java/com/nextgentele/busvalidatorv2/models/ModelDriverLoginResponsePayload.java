
package com.nextgentele.busvalidatorv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelDriverLoginResponsePayload {

    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("last_updated_on")
    @Expose
    private String lastUpdatedOn;
    @SerializedName("backendKey")
    @Expose
    private String backendKey;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("joined_on")
    @Expose
    private String joinedOn;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_emei_used")
    @Expose
    private String lastEmeiUsed;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getBackendKey() {
        return backendKey;
    }

    public void setBackendKey(String backendKey) {
        this.backendKey = backendKey;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(String joinedOn) {
        this.joinedOn = joinedOn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastEmeiUsed() {
        return lastEmeiUsed;
    }

    public void setLastEmeiUsed(String lastEmeiUsed) {
        this.lastEmeiUsed = lastEmeiUsed;
    }

}
