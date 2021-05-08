package com.nextgentele.busvalidatorv2.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.nextgentele.busvalidatorv2.R;
import com.nextgentele.busvalidatorv2.db.MyDb;
import com.nextgentele.busvalidatorv2.db.Users;
import com.nextgentele.busvalidatorv2.fragment.CardExitFragment;
import com.nextgentele.busvalidatorv2.fragment.QRExitFragment;
import com.nextgentele.busvalidatorv2.models.ModelListActiveSjtTicketPayload;
import com.nextgentele.busvalidatorv2.roomdb.AppDatabase;
import com.nextgentele.busvalidatorv2.util.ConnectionDetector;
import com.nextgentele.busvalidatorv2.util.UartClass;


public class ThirdActivity extends AppCompatActivity {

    Users users;
    ModelListActiveSjtTicketPayload models;
    AppDatabase appDatabase;
    MyDb myDb;
    UartClass uartClass;
    String flag = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        flag = getIntent().getStringExtra("flag");
        appDatabase = AppDatabase.getAppDatabase(this);
        uartClass = new UartClass();

//        currentBalance = findViewById(R.id.aCardBal_activity_second);
//        fromTo = findViewById(R.id.fareFromto_third_activity);
//        charges = findViewById(R.id.fareDeduct_activity_second);
//        finalBalance = findViewById(R.id.newBalance_activity_second);
        myDb = new MyDb(this);

        String from = "Dwarka";
        String to = "";//AppPreferences.getAppPrefrences(VariablesConstant.CURRENT_STOP_ID,this);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment fragment = null;
        Bundle bundle=new Bundle();

        if (flag.equals("cards")) {
            users = (Users) getIntent().getSerializableExtra("user");
            if (users != null) {
                fragment=new CardExitFragment();
                bundle.putSerializable("user",users);
                fragment.setArguments(bundle);
                java.util.Date date = new java.util.Date();
                long dateTimeOut = date.getTime();
                double charge = 25.00f;
//                String fare=appDatabase.fareRulesListDao().getFare(users.getStopIn(),to);
//                myDb.update(users.getUserName(), dateTimeOut, to, Double.parseDouble(fare));

//                currentBalance.setText("INR " + users.getUserBal());
//                fromTo.setText("Fare deducted from " + from + " to " + to);
//            sendData(users.getUserName());
//                charges.setText("INR " + charge);
                double bal = users.getUserBal() - charge;
//                finalBalance.setText("INR " + bal);

                int result = myDb.deleteUser(users.getUserName());
                Log.i("Delete", "User");
                if (result > 0) {
                    Log.i("ThirdActivity", "Deleted Data");
//                    Toast.makeText(com.nextgentele.busvalidator.activity.ThirdActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                }

            }
        } else {
            //QR Reader
            models= (ModelListActiveSjtTicketPayload) getIntent().getSerializableExtra("data");
            fragment=new QRExitFragment();
            bundle.putSerializable("qrTicket",models);
            fragment.setArguments(bundle);
        }
        if (fragment!=null){
        fragmentTransaction.replace(R.id.fragment,fragment);
        fragmentTransaction.commitAllowingStateLoss();}else{}


        // TODO Auto-generated method stub
        //            Intent intent=new Intent(com.nextgentele.busvalidator.activity.ThirdActivity.this, com.nextgentele.busvalidator.activity.MainActivity.class);
        //            startActivity(intent);
        new Handler().postDelayed(this::finish, 3000);
    }

    public void sendData(String username) {
        Users users = myDb.getUser(username);
        ConnectionDetector connectionDetector = new ConnectionDetector(this);
        if (connectionDetector.isConnectingToInternet()) {
            Toast.makeText(this, "Internet access", Toast.LENGTH_SHORT).show();

        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        uartClass.uart_close();
    }

}