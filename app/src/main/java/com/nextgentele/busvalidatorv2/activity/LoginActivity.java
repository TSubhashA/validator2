package com.nextgentele.busvalidatorv2.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nextgentele.busvalidatorv2.R;

import com.nextgentele.busvalidatorv2.adapter.MyAdapter;
import com.nextgentele.busvalidatorv2.callback.LoginCallBack;
import com.nextgentele.busvalidatorv2.constants.Constants;
import com.nextgentele.busvalidatorv2.fragment.LoginIDFragment;
import com.nextgentele.busvalidatorv2.presenter.LoginManager;
import com.nextgentele.busvalidatorv2.roomdb.AppDatabase;
import com.nextgentele.busvalidatorv2.util.CheckPermission;
import com.nextgentele.busvalidatorv2.util.ConnectionDetector;
import com.nextgentele.busvalidatorv2.util.CustomDialogue;
import com.nextgentele.busvalidatorv2.util.PermissionManagerUtil;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity implements LoginIDFragment.LoginIdListener,LoginCallBack {

    private static final String TAG =LoginActivity.class.getSimpleName() ;
    Button btn;
    TabLayout tabLayout;
    ViewPager viewPager;
    ConnectionDetector cd;
    PermissionManagerUtil pm;
    SpotsDialog progressBar;

    private static final int GPS_PERMISSION = 100;
    private static final int STORAGE_PERMISSION = 101;
    private static final int GPS_PERMISSION_COARPSE = 102;

    CheckPermission checkPermission;

    LoginManager loginManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkPermission = new CheckPermission(this);



        cd=new ConnectionDetector(this);
        pm=new PermissionManagerUtil(this);

        pm.showPhoneStatePermission();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Login ID"));
        tabLayout.addTab(tabLayout.newTab().setText("mPin"));
        progressBar=new SpotsDialog(this,R.style.CustomDialogue);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        loginManager = new LoginManager(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            String[] Permissions = new String[]{ String.valueOf(Manifest.permission.ACCESS_FINE_LOCATION)
            ,String.valueOf(Manifest.permission.ACCESS_COARSE_LOCATION)
            , String.valueOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)};

            checkPermission.checkPermission(GPS_PERMISSION,Permissions);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == GPS_PERMISSION) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(LoginActivity.this,
                        "GPS Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(LoginActivity.this,
                        "GPS Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == STORAGE_PERMISSION) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(LoginActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(LoginActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == GPS_PERMISSION_COARPSE) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(LoginActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(LoginActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onbuttonLogin(String mobile, String pass) {
        progressBar.show();

 loginManager.Login(mobile, pass);
    }

    @Override
    public void onLoginSuccess() {
        progressBar.dismiss();
        Intent intent=new Intent(this,RouteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
//        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(String error) {
        progressBar.dismiss();
        Log.w(TAG,"onError : " + error);
        CustomDialogue.showDialog(this,error);
    }

}