package com.nextgentele.busvalidatorv2.scannerqr;

import android.os.Handler;
import android.util.Log;

import com.nextgentele.busvalidatorv2.activity.MainActivity;
import com.nextgentele.busvalidatorv2.models.Stop;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android_serialport_api.SerialPort;


public class HwSerialPort {

    public static String TAG = "SerialPortUtil";
    MainActivity view;
    TicketValidation ticketValidation;
//    List<Stop> stops;
//    int index;

    String path = "/dev/ttyHSL3";
    int baudrate = 115200;
    int flags = 0;
    /**
     * 标记当前串口状态(true:打开,false:关闭)
     **/
    public static boolean isFlagSerial = false;

    public static SerialPort serialPort = null;
    public static InputStream inputStream = null;
    public static OutputStream outputStream = null;
    public static Thread receiveThread = null;
    public static String strData = "";
    public static Handler mHandler;

    public HwSerialPort(MainActivity view) {
        this.view = view;
//        this.stops = stops;
        ticketValidation = new TicketValidation(view);
    }


    /**
     * 打开串口
     *
     * @parampath:/dev/ttyHSL2 ------外接串口
     * /dev/ttyHSL3  ------扫码串口
     */
    public boolean open() {
        boolean isopen = false;
        if (isFlagSerial) {
            Log.e(TAG, "SerialPort : open");
            return false;
        }
        try {
            serialPort = new SerialPort(new File(path), baudrate, flags);
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
            receive();
            isopen = true;
            isFlagSerial = true;
        } catch (IOException e) {
            e.printStackTrace();
            isopen = false;
        }
        return isopen;
    }

    /**
     * 关闭串口
     */
    public static boolean close() {
        if (isFlagSerial) {
            Log.e(TAG, "Close");
            return false;
        }
        boolean isClose = false;
        Log.e(TAG, "Open");
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            isClose = true;
            isFlagSerial = false;//关闭串口时，连接状态标记为false
        } catch (IOException e) {
            e.printStackTrace();
            isClose = false;
        }
        return isClose;
    }

    /**
     * 发送串口指令
     */
    public void sendString(String data, Handler handler) {
        mHandler = handler;
        if (!isFlagSerial) {
            Log.e(TAG, "串口未打开,发送失败" + data);
            return;
        }
        try {
            outputStream.write(ByteUtil.hex2byte(data));
            outputStream.flush();
            Log.e(TAG, "sendSerialData:" + data);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "发送指令出现异常");
        }
    }

    /**
     * 接收串口数据的方法
     */
    public void receive() {
        if (receiveThread != null && !isFlagSerial) {
            return;
        }
        receiveThread = new Thread() {
            @Override
            public void run() {
                while (isFlagSerial) {
                    try {
                        byte[] readData = new byte[128];
                        if (inputStream == null) {
                            return;
                        }
                        int size = inputStream.read(readData);
                        if (size > 0 && isFlagSerial) {
                            strData = ByteUtil.byteToStr(readData, size);
                            String data = ByteUtil.hexStringToString(strData);
                            Log.e(TAG, "readSerialData:" + data);
                            ticketValidation.checkJTTicket(data);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        receiveThread.start();
    }

}
