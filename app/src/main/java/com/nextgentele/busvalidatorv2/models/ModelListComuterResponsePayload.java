
package com.nextgentele.busvalidatorv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelListComuterResponsePayload {

    @SerializedName("textual_identifier")
    @Expose
    private String textualIdentifier;
    @SerializedName("display_order")
    @Expose
    private Integer displayOrder;
    @SerializedName("backendKey")
    @Expose
    private String backendKey;

    public String getTextualIdentifier() {
        return textualIdentifier;
    }

    public void setTextualIdentifier(String textualIdentifier) {
        this.textualIdentifier = textualIdentifier;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getBackendKey() {
        return backendKey;
    }

    public void setBackendKey(String backendKey) {
        this.backendKey = backendKey;
    }

}
