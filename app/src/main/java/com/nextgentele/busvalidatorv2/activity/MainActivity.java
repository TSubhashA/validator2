package com.nextgentele.busvalidatorv2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nextgentele.busvalidatorv2.R;
import com.nextgentele.busvalidatorv2.adapter.MySpinnerAdapter;
import com.nextgentele.busvalidatorv2.callback.LocationCallBack;
import com.nextgentele.busvalidatorv2.fragment.ErrorFragment;
import com.nextgentele.busvalidatorv2.fragment.MainFragment;
import com.nextgentele.busvalidatorv2.models.Stop;
import com.nextgentele.busvalidatorv2.pref.AppPreferences;
import com.nextgentele.busvalidatorv2.pref.VariablesConstant;
import com.nextgentele.busvalidatorv2.presenter.StopKey;
import com.nextgentele.busvalidatorv2.roomdb.AppDatabase;
import com.nextgentele.busvalidatorv2.scannerqr.HwSerialPort;
import com.nextgentele.busvalidatorv2.service.MyServiceUpdateTicket;
import com.nextgentele.busvalidatorv2.service.MyServiceUploadScan;
import com.nextgentele.busvalidatorv2.util.GeoFencing;
import com.nextgentele.busvalidatorv2.util.ServiceCheck;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainActivity extends AppCompatActivity implements LocationCallBack {

    private static final String TAG = MainActivity.class.getSimpleName();
    private HwSerialPort mHwSerialPort;
    TextView prevStoptv, currentStoptv, nextStoptv;
    int stopIndex = 0;
    TextView tvbusno, tvFrom;//, tvTo;

    boolean isEntryExitEnable;
    AppDatabase appDatabase;
    GeoFencing geoFencing;

    List<Stop> stopList = new ArrayList<>();
    Intent intent, intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = AppDatabase.getAppDatabase(this);

        tvbusno = findViewById(R.id.route_activity_main);

        tvFrom = findViewById(R.id.source_activity_main);

        currentStoptv = findViewById(R.id.current_stop_activity_main);
        nextStoptv = findViewById(R.id.next_stop_activity_main);
        prevStoptv = findViewById(R.id.prev_stop_activity_main);

        getStopName();

        mHwSerialPort = new HwSerialPort(this);
        mHwSerialPort.open();


        intent = new Intent(this, MyServiceUpdateTicket.class);
        intent1 = new Intent(this, MyServiceUploadScan.class);

        if (ServiceCheck.isServiceRunning(MyServiceUpdateTicket.class, this)) {
            startService(intent);
        }
        if (ServiceCheck.isServiceRunning(MyServiceUploadScan.class, this)) {
            startService(intent1);
        }

        String fromto = AppPreferences.getAppPrefrences(VariablesConstant.Source_Destination, this);
        tvFrom.setText(fromto);

        String route = AppPreferences.getAppPrefrences(VariablesConstant.BUS_No, this);
        tvbusno.setText(route);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment1 = new MainFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment1);
        fragmentTransaction.commit();

    }

    private void getStopName() {
        Log.w(TAG, " getStops : ");
        appDatabase.stopsDao().getStopsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<Stop>>() {
                    @Override
                    public void onNext(List<Stop> stops) {
                        Log.w(TAG, " getStops : onNext");
                        stopList.addAll(stops);
                        updateStops();
                        geoFencing = new GeoFencing(stopList, MainActivity.this, stopIndex);
                        geoFencing.requestUpdate();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, " getStops : onError " + t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.w(TAG, " getStops : onSucccess");
                    }
                });

    }

    public void updateStops() {
        Log.w(TAG, " setText : " + stopIndex);
        Log.w(TAG, " StopSize : " + stopList.size());

        if (stopIndex == 0) {
            prevStoptv.setText("START");
        } else
            prevStoptv.setText(String.valueOf(stopList.get(stopIndex - 1).getTextualIdentifier()));

        currentStoptv.setText(String.valueOf(stopList.get(stopIndex).getTextualIdentifier()));

        AppPreferences.setAppPrefrences(VariablesConstant.CURRENT_STOP_KEY, StopKey.getCurrentStopKey(this,stopIndex + 1), this);
        AppPreferences.setAppPrefrences(VariablesConstant.CURRENT_STOP, String.valueOf(stopList.get(stopIndex).getTextualIdentifier()), this);

        if (stopIndex == stopList.size() - 1) {
            nextStoptv.setText("END");
        } else
            nextStoptv.setText(String.valueOf(stopList.get(stopIndex + 1).getTextualIdentifier()));
    }


    @Override
    protected void onResume() {
        super.onResume();
        Action();
    }

    private void Action() {
        new Thread(() -> mHwSerialPort.receive()).start();
    }


    public void changeStop(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        View mv = getLayoutInflater().inflate(R.layout.update_stop, null);
        Spinner spinner = mv.findViewById(R.id.spinner);
        List<String> label = new ArrayList<>();
        label.add("Select the Stop");
        for (Stop stop : stopList)
            label.add(stop.getTextualIdentifier());

        // Creating adapter for spinner
        MySpinnerAdapter dataAdapter1 = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item, label);

        spinner.setAdapter(dataAdapter1);
        spinner.setSelection(stopIndex + 1);
        Button button = mv.findViewById(R.id.update_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItemPosition() != 0) {
                    stopIndex = spinner.getSelectedItemPosition() - 1;
                    Log.e(TAG, "onDialog : " + stopIndex);
                    updateStops();
                    geoFencing.setIndex(stopIndex);
                    dialog.dismiss();
                }
            }
        });

        dialog.setView(mv);
        dialog.show();
    }

    public void showDialog(String s) {
        Bundle bundle = new Bundle();
        bundle.putString("error", s);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = new ErrorFragment();
        fragment.setArguments(bundle);
        ft.replace(R.id.fragment, fragment, "errorFragment");
        ft.commitAllowingStateLoss();

        Handler handler = new Handler();
        Runnable runnable = () -> {
            FragmentTransaction ft1 = fragmentManager.beginTransaction();
            ErrorFragment ef = (ErrorFragment) getSupportFragmentManager().findFragmentByTag("errorFragment");
            ft1.replace(R.id.fragment, new MainFragment());
            assert ef != null;
            if (ef.isVisible()) {
                fragmentManager.popBackStack();
                ft1.commitAllowingStateLoss();
            }
        };
        handler.postDelayed(runnable, 2500);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public int getStopIndex() {
        return stopIndex + 1;
    }

    @Override
    public void changeStop(Stop stop) {
        stopIndex = stopList.indexOf(stop);
        updateStops();
    }

    @Override
    public void setEntryExitEnable(boolean isEntryExit) {
        isEntryExitEnable = isEntryExit;
    }

    public boolean isEntryExitEnable() {
        return isEntryExitEnable;
    }


}