package com.nextgentele.busvalidatorv2.callback;

import com.nextgentele.busvalidatorv2.models.Stop;

public interface LocationCallBack {
    void changeStop(Stop stop);
    void setEntryExitEnable(boolean isEntryExit);
}
