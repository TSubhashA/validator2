package com.nextgentele.busvalidatorv2.util;

import android.util.Log;

import com.newcapec.b601.sys.HwGpio;
import com.newcapec.b601.sys.HwScanner;
import com.nextgentele.busvalidatorv2.activity.MainActivity;


public class QRReader extends MainActivity
{
    String TAG ;
    private int TestType = 0;
    boolean ifEnd = false;
    final private int UART_PORT  = 1;
    final private int BAUD_RATE = 115200;
    private boolean mOpenFlag = false;
    HwScanner serial;
    HwGpio hw;

//    TicketValidation ticketValidation;

    MainActivity context;

    public QRReader(MainActivity context) {
        this.context = context;
//        ticketValidation=new TicketValidation(context);
    }

    public void scanner_start() {

        int ret = serial.open(BAUD_RATE, 1);
        mOpenFlag = ret == 0;
        Runnable runnable1 = new Runnable() {
            @Override
            public void run(){
                byte[] pszBuf1 = new byte[1000];
                int count = 0;
                ifEnd = false;
                while (!ifEnd) {
                    try{
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pszBuf1 = serial.recvMsg();
                    if( pszBuf1 == null ) {
                        continue;
                    }
                    count ++;
                    try
                    {
                        String recvStr = new String(pszBuf1).trim();
                        if( recvStr.length() > 0 ){
                            Log.i("received",recvStr);
                            //Toast.makeText(context,  "recv "+count+": "+recvStr , Toast.LENGTH_SHORT).show();
//                            ticketValidation.checkJTTicket(recvStr);
                        }
                    } catch(NullPointerException e)	{
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnable1).start();
    }

    public void scanner_pwron() {
        hw.HW_QrdCom_PwrEn(1);
        hw.HW_UartMode_Set(HwGpio.UART_MODE_QRCODE);
        if(serial==null) {
            serial = new HwScanner();
        }
        scanner_start();
    }
    public void scanner_stop() {
        ifEnd = true;
        serial.close();
        hw.HW_QrdCom_PwrEn(0);
    }
    public void init(){
        hw = new HwGpio();
        scanner_pwron();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ifEnd = true;
        serial.close();
        hw.HW_QrdCom_PwrEn(0);
    }
}
