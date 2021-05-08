package com.nextgentele.busvalidatorv2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nextgentele.busvalidatorv2.R;
import com.nextgentele.busvalidatorv2.adapter.MySpinnerAdapter;
import com.nextgentele.busvalidatorv2.callback.RouteCallBack;
import com.nextgentele.busvalidatorv2.pref.AppPreferences;
import com.nextgentele.busvalidatorv2.pref.VariablesConstant;
import com.nextgentele.busvalidatorv2.presenter.MasterManager;
import com.nextgentele.busvalidatorv2.presenter.RouteManager;
import com.nextgentele.busvalidatorv2.util.ConnectionDetector;
import com.nextgentele.busvalidatorv2.util.CustomDialogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class RouteActivity extends AppCompatActivity implements RouteCallBack {

    ConnectionDetector cd;

    private static final String TAG = RouteActivity.class.getSimpleName();
    Button submmit;
    RouteManager routeManager;
    Spinner spinnerDepot, spinnerRoute;
    SpotsDialog spotsDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    List<String> labels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        submmit = findViewById(R.id.submitbtn);
        spinnerDepot = findViewById(R.id.spinner1);
        spinnerRoute = findViewById(R.id.spinner2);
        cd=new ConnectionDetector(this);

        routeManager = new RouteManager(this);
        spotsDialog = new SpotsDialog(this, R.style.CustomDialogue);
        spotsDialog.show();
        spinnerDepot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {
                    spotsDialog.show();
                    if (cd.isConnectingToInternet())
                    routeManager.getRoutes((String) spinnerDepot.getItemAtPosition(i));
                    else onError("No Internet Connection");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        loadSpinnerData();

        submmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (spinnerRoute.getSelectedItemPosition()!=0){
                   spotsDialog.show();
                   if (cd.isConnectingToInternet()) {
                       AppPreferences.setAppPrefrences(VariablesConstant.ROUTE_NO, String.valueOf(spinnerRoute.getSelectedItem()), RouteActivity.this);
                       Log.w(TAG, " onClick : " + spinnerRoute.getSelectedItem());
                       routeManager.getRouteInfo(hashMap.get(spinnerRoute.getSelectedItem()));
                   }
                    else onError("No Internet Connection");
               }
            }
        });


    }


    private void loadSpinnerData() {
        spotsDialog.dismiss();

        labels=routeManager.getDepot();
        labels.add("Select the Depot");

        // Creating adapter for spinner
        MySpinnerAdapter dataAdapter = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item, labels);

//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepot.setSelection(0);
        spinnerDepot.setAdapter(dataAdapter);

    }

    @Override
    public void setRouteSpinner(HashMap<String, String> hashMap) {
        this.hashMap = hashMap;
        List<String> label = new ArrayList<>();
        label.add("Select the Route");
        label.addAll(hashMap.keySet());

        // Creating adapter for spinner
        MySpinnerAdapter dataAdapter1 = new MySpinnerAdapter(this, android.R.layout.simple_spinner_item, label);

//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoute.setSelection(0);
        spinnerRoute.setAdapter(dataAdapter1);
        spotsDialog.dismiss();
    }

    @Override
    public void onLoginSuccess() {

        MasterManager masterManager=new MasterManager(RouteActivity.this);
        spotsDialog.dismiss();
        Toast.makeText(this, "onLoginSuccess", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(RouteActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onError(String error) {
        spotsDialog.dismiss();
        CustomDialogue.showDialog(this, error);
    }

}