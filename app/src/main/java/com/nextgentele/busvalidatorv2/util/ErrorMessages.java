package com.nextgentele.busvalidatorv2.util;


public class ErrorMessages {

    public static String messagesTodisplay(Emsg errKey) {

        String err = "";

        switch (errKey) {
            case NotAValidTicket:
                err = "NotAValidTicket";
                break;
            case NotARouteTicket:
                err = "NotARouteTicket";
                break;
            case StopNotArrived:
                err = "StopNotArrived";
                break;
            case UsedTicket:
                err = "UsedTicket";
                break;
            case NotEligibleToBoard:
                err = "NotEligibleToBoard";
                break;
            case NotEligibleToAlight:
                err = "NotEligibleToAlight";
                break;

        }

        Emessage error = Emessage.getErrMessage(err);
        return error.getValue();
    }
}
