package com.nextgentele.busvalidatorv2.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nextgentele.busvalidatorv2.R;
import com.nextgentele.busvalidatorv2.db.MyDb;
import com.nextgentele.busvalidatorv2.db.Users;
import com.nextgentele.busvalidatorv2.fragment.CardEntryFragment;
import com.nextgentele.busvalidatorv2.fragment.QREntryFragment;

import com.nextgentele.busvalidatorv2.models.ModelListActiveSjtTicketPayload;
import com.nextgentele.busvalidatorv2.util.UartClass;


public class SecondActivity extends AppCompatActivity {

//    TextView availableBalance, tvYourBalance;
//    TextView ok;
//    LinearLayout buttonView, textViewInsifficient;
    Users users;
    MyDb myDb;
    ModelListActiveSjtTicketPayload models;
    UartClass uartClass;
String flag;
    boolean ifEnd;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

//        availableBalance = findViewById(R.id.aCardBal_activity_second);
//        tvYourBalance=findViewById(R.id.yourBalance);
//        ok=findViewById(R.id.btnOk);
//        buttonView = findViewById(R.id.okButton);
//        textViewInsifficient=findViewById(R.id.insufficientText);
//        uartClass=new UartClass();

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment fragment = null;
        Bundle bundle=new Bundle();
        flag=getIntent().getStringExtra("flag");
        if (flag.equals("cards")){
        users= (Users) getIntent().getSerializableExtra("user");
        bundle.putSerializable("user",users);
        fragment=new CardEntryFragment();
        fragment.setArguments(bundle);
        if (users!=null){
//        availableBalance.setText("INR "+ users.getUserBal());
        if (users.getUserBal()<10){
//            buttonView.setVisibility(View.GONE);
//            textViewInsifficient.setVisibility(View.VISIBLE);
        }else{
//            buttonView.setVisibility(View.VISIBLE);
//            textViewInsifficient.setVisibility(View.GONE);
            myDb=new MyDb(this);
            boolean result= myDb.insertData(users);
            if (!result){
                Log.e("SecondActivity","Data Not inserted");
            }else {
                Log.i("SecondActivity","Data inserted");
            }
        }}}
        else if(flag.equals("qr")){
            models= (ModelListActiveSjtTicketPayload) getIntent().getSerializableExtra("data");

            bundle.putSerializable("qrTicket",models);
            fragment=new QREntryFragment();
            fragment.setArguments(bundle);
        }
        else if(flag.equals("value")) {
            bundle.putString("flag","value");
            bundle.putString("balance",getIntent().getStringExtra("balance"));
            fragment=new QREntryFragment();
            fragment.setArguments(bundle);

        }else
        {}

        fragmentTransaction.replace(R.id.fragment,fragment);
        fragmentTransaction.commitAllowingStateLoss();

//        uartClass.init();
//        uartClass.uart_open();
//        uartClass.uart_send("GO11");
//        uartClass.uart_close();

        String res="PASS";
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                   finish();


            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        uartClass.uart_close();
    }
}