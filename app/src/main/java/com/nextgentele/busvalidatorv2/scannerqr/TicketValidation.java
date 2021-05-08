package com.nextgentele.busvalidatorv2.scannerqr;

import android.content.Intent;
import android.util.Log;

import com.nextgentele.busvalidatorv2.activity.MainActivity;
import com.nextgentele.busvalidatorv2.activity.SecondActivity;
import com.nextgentele.busvalidatorv2.activity.ThirdActivity;
import com.nextgentele.busvalidatorv2.models.ModelListActiveSjtTicketPayload;
import com.nextgentele.busvalidatorv2.presenter.TicketValidationManager;
import com.nextgentele.busvalidatorv2.util.Emsg;
import com.nextgentele.busvalidatorv2.util.ErrorMessages;
import com.nextgentele.busvalidatorv2.util.TicketType;
import com.nextgentele.busvalidatorv2.util.types;
import com.subhasha.mylibraryencdec.AESUtil;

import java.io.Serializable;

import javax.crypto.IllegalBlockSizeException;

public class TicketValidation extends MainActivity {
    private final String TAG = TicketValidation.class.getSimpleName();
    String[] decStrings = null;
    String qrHash;
    String ticketType;
    TicketValidationManager ticketValidationManager;
    MainActivity view;
    ModelListActiveSjtTicketPayload model = null;

    public TicketValidation(MainActivity view) {
        this.view = view;
        ticketValidationManager = new TicketValidationManager(this);
    }

    public void checkJTTicket(String qr) {
        Log.w(TAG, " qr checking Started : " + qr);
        String decHash = null;
        try {
            decHash = AESUtil.decryptValue(qr);
            decStrings = decHash.split(TicketType.suffixString);
            qrHash = decStrings[0];
            ticketType = decStrings[1];
            Log.w(TAG, " onType : " + ticketType);
            Log.w(TAG, " onQr : " + qrHash);
            if (ticketType.equals(TicketType.tTypes(types.SJT))) {
                model = ticketValidationManager.sjtTicket(qrHash);
            } else if (ticketType.equals(TicketType.tTypes(types.RJT))) {
                model = ticketValidationManager.rjtTicket(qrHash);
            }
            if (view.isEntryExitEnable()) {
                if (model != null) {
                    if (ticketType.equals(TicketType.tTypes(types.SJT)) || ticketType.equals(TicketType.tTypes(types.RJT))) {
                        int srcId = ticketValidationManager.getStopOrder(model.getSrcStopTextualIdentifier());
                        Log.w(TAG, " srcId : " + srcId);
                        int destId = ticketValidationManager.getStopOrder(model.getDestStopTextualIdentifier());
                        if (ticketType.equals("RJT")) {
                            int tempId = srcId;
                            srcId = destId;
                            destId = tempId;
                        }
                        if (model.getEntryAvailable()) {
                            Log.w(TAG, " index : " + view.getStopIndex());
                            if (view.getStopIndex() >= srcId && view.getStopIndex() < destId) {
                                //entry Available
                                model.setEntryAvailable(false);
                                Intent intent = new Intent(view, SecondActivity.class);
                                intent.putExtra("data", (Serializable) model);
                                ticketValidationManager.updateTicket(model);
                                updateTicket(intent,"qr");

                            } else {
                                //no entry available
                                runOnUiThread(() -> view.showDialog(ErrorMessages.messagesTodisplay(Emsg.NotEligibleToBoard)));
                            }
                        } else if (model.getExitAvailable()) {
                            if (destId >= view.getStopIndex() && view.getStopIndex() >= srcId) {
                                //exit available
                                model.setExitAvailable(false);
                                Intent intent = new Intent(view, ThirdActivity.class);
                                intent.putExtra("data", (Serializable) model);
                                ticketValidationManager.updateTicket(model);
                                updateTicket(intent,"qr");

                            } else {
                                //no exit available
                                runOnUiThread(() -> view.showDialog(ErrorMessages.messagesTodisplay(Emsg.NotEligibleToAlight)));
                            }
                        } else {
                            Log.d(TAG, "Ticket Already Consumed");
                            runOnUiThread(() -> view.showDialog(ErrorMessages.messagesTodisplay(Emsg.UsedTicket)));
                        }
                    }

                }  //ValueTicket Logic
                else if (ticketType.equals(TicketType.tTypes(types.ValueTicket))) {

                    ticketValidationManager.validateWallet(qrHash);

                } else {
                    Log.e(TAG, " No Ticket Available");
                    runOnUiThread(() -> view.showDialog(ErrorMessages.messagesTodisplay(Emsg.NotARouteTicket)));
                }

            } else {
                Log.e(TAG, " Please Wait for the bus to arrive at the stop");
                runOnUiThread(() -> view.showDialog(ErrorMessages.messagesTodisplay(Emsg.StopNotArrived)));

            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "onError : " + e.getMessage());
            if (e instanceof IllegalBlockSizeException) {
                runOnUiThread(() -> view.showDialog(ErrorMessages.messagesTodisplay(Emsg.NotAValidTicket)));
            }

        }
    }

   public void updateValletQR(int i,String message,String balance, String charge)
    {
     if (i==1)//Entry
     {
         Intent intent = new Intent(view, SecondActivity.class);
         intent.putExtra("balance", balance);
         updateTicket(intent,"value");
     }
     else if (i==2)//Exit
     {
         Intent intent = new Intent(view, ThirdActivity.class);
         intent.putExtra("balance", balance);
         intent.putExtra("fare", charge);
         updateTicket(intent,"value");
     }
     else
     {
         runOnUiThread(() -> view.showDialog(message));
     }
    }

    void updateTicket(Intent intent,String flag) {
        intent.putExtra("flag", flag);
        view.startActivity(intent);
    }


}
