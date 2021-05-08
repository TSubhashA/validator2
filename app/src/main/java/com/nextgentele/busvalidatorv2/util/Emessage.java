package com.nextgentele.busvalidatorv2.util;

import androidx.annotation.Nullable;

public enum Emessage {

    NotAValidTicket("Your ticket is not a valid Ticket Please buy a correct ticket "),
    NotARouteTicket("Your ticket is not good for this route. Please buy a correct ticket"),
    StopNotArrived("Please Wait for the bus to arrive at the stop"),
    UsedTicket("Sorry, your ticket already been used. Please buy another one"),
    NotEligibleToBoard("Not Eligible to Board at this stop \nPlease connect with Bus Conductor"),
    NotEligibleToAlight("Not Eligible to alight at this stop \nPlease connect with Bus Conductor");

    private final String value;

     Emessage(String value) {
        this.value = value;
    }

    public static Emessage getErrMessage(@Nullable String value){
        return Emessage.valueOf(value);
    }

    public String getValue() {
        return value;
    }

}
