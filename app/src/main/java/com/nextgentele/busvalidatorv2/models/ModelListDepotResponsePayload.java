
package com.nextgentele.busvalidatorv2.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



@Entity(tableName = "depotList")
public class ModelListDepotResponsePayload {

    @SerializedName("numeric_identifier")
    @PrimaryKey(autoGenerate = false)
    @NonNull
     String numericIdentifier;
    @SerializedName("stop_category")
    @Expose
    private String stopCategory;
    @SerializedName("backendKey")
    @Expose
    private String backendKey;
    @SerializedName("textual_Identifier")
    @Expose
    private String textualIdentifier;

    public ModelListDepotResponsePayload(@NonNull String numericIdentifier, String stopCategory, String backendKey, String textualIdentifier) {
        this.numericIdentifier = numericIdentifier;
        this.stopCategory = stopCategory;
        this.backendKey = backendKey;
        this.textualIdentifier = textualIdentifier;
    }

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

    public String getBackendKey() {
        return backendKey;
    }

    public void setBackendKey(String backendKey) {
        this.backendKey = backendKey;
    }

    public String getTextualIdentifier() {
        return textualIdentifier;
    }

    public void setTextualIdentifier(String textualIdentifier) {
        this.textualIdentifier = textualIdentifier;
    }

}
