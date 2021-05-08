package com.nextgentele.busvalidatorv2.callback;

import java.util.HashMap;

public interface RouteCallBack {

    void setRouteSpinner(HashMap<String, String> hashMap);
    void onLoginSuccess();
    void onError(String error);
}
