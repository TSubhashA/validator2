
package com.nextgentele.busvalidatorv2.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelListRouteInfoResponsePayload {

    @SerializedName("numeric_identifier")
    @Expose
    private String numericIdentifier;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("max_fare")
    @Expose
    private String maxFare;
    @SerializedName("min_fare")
    @Expose
    private String minFare;
    @SerializedName("last_trip_start_timing")
    @Expose
    private String lastTripStartTiming;
    @SerializedName("Stops")
    @Expose
    private List<Stop> stops = null;
    @SerializedName("first_trip_start_timing")
    @Expose
    private String firstTripStartTiming;
    @SerializedName("single_trip_time_minutes")
    @Expose
    private String singleTripTimeMinutes;
    @SerializedName("backendKey_origin_depot")
    @Expose
    private String backendKeyOriginDepot;
    @SerializedName("textual_Identifier")
    @Expose
    private String textualIdentifier;
    @SerializedName("single_trip_distance")
    @Expose
    private String singleTripDistance;
    @SerializedName("backendKey_route")
    @Expose
    private String backendKeyRoute;

    public String getNumericIdentifier() {
        return numericIdentifier;
    }

    public void setNumericIdentifier(String numericIdentifier) {
        this.numericIdentifier = numericIdentifier;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getMaxFare() {
        return maxFare;
    }

    public void setMaxFare(String maxFare) {
        this.maxFare = maxFare;
    }

    public String getMinFare() {
        return minFare;
    }

    public void setMinFare(String minFare) {
        this.minFare = minFare;
    }

    public String getLastTripStartTiming() {
        return lastTripStartTiming;
    }

    public void setLastTripStartTiming(String lastTripStartTiming) {
        this.lastTripStartTiming = lastTripStartTiming;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public String getFirstTripStartTiming() {
        return firstTripStartTiming;
    }

    public void setFirstTripStartTiming(String firstTripStartTiming) {
        this.firstTripStartTiming = firstTripStartTiming;
    }

    public String getSingleTripTimeMinutes() {
        return singleTripTimeMinutes;
    }

    public void setSingleTripTimeMinutes(String singleTripTimeMinutes) {
        this.singleTripTimeMinutes = singleTripTimeMinutes;
    }

    public String getBackendKeyOriginDepot() {
        return backendKeyOriginDepot;
    }

    public void setBackendKeyOriginDepot(String backendKeyOriginDepot) {
        this.backendKeyOriginDepot = backendKeyOriginDepot;
    }

    public String getTextualIdentifier() {
        return textualIdentifier;
    }

    public void setTextualIdentifier(String textualIdentifier) {
        this.textualIdentifier = textualIdentifier;
    }

    public String getSingleTripDistance() {
        return singleTripDistance;
    }

    public void setSingleTripDistance(String singleTripDistance) {
        this.singleTripDistance = singleTripDistance;
    }

    public String getBackendKeyRoute() {
        return backendKeyRoute;
    }

    public void setBackendKeyRoute(String backendKeyRoute) {
        this.backendKeyRoute = backendKeyRoute;
    }

}
