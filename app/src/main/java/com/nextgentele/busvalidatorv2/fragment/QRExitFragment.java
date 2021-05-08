package com.nextgentele.busvalidatorv2.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.nextgentele.busvalidatorv2.R;
import com.nextgentele.busvalidatorv2.models.ModelListActiveSjtTicketPayload;
import com.nextgentele.busvalidatorv2.pref.AppPreferences;
import com.nextgentele.busvalidatorv2.pref.VariablesConstant;
import com.nextgentele.busvalidatorv2.presenter.ExitManager;
import com.nextgentele.busvalidatorv2.roomdb.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QRExitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QRExitFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QRExitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QRExitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QRExitFragment newInstance(String param1, String param2) {
        QRExitFragment fragment = new QRExitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ExitManager exitManager=new ExitManager(getContext());
        View view= inflater.inflate(R.layout.fragment_q_r_exit, container, false);

        assert getArguments() != null;
        ModelListActiveSjtTicketPayload models= (ModelListActiveSjtTicketPayload) getArguments().getSerializable("qrTicket");
        exitManager.updateExit(models);
        TextView balance=view.findViewById(R.id.bal_amout_fragment_qr);

        LinearLayout balLayout=view.findViewById(R.id.bal_ll);
        LinearLayout srcLLayout=view.findViewById(R.id.src_ll);
//        if (qrTicketMaster.getTicketType().equals("valueTicket"))
//        {
//            balLayout.setVisibility(View.VISIBLE);
//            balance.setText(qrTicketMaster.getTktAmnt());
//        }

        TextView ticketTypeTV =view.findViewById(R.id.ticket_type_fragment_qr);
        TextView srcTV = view.findViewById(R.id.src_stop_fragment_qr);
        TextView destTV = view.findViewById(R.id.dest_stop_fragment_qr);
        TextView fareTV = view.findViewById(R.id.fare_amout_fragment_qr);
        TextView dateTV=view.findViewById(R.id.date_fragment_qr);
        setDate(dateTV);
        if (models!=null) {
            if (models.getRjtBooked())
                ticketTypeTV.setText("RJT");
            else
                ticketTypeTV.setText("SJT");

            fareTV.setText(models.getFare());
            srcTV.setText(models.getSrcStopTextualIdentifier());
            destTV.setText(models.getDestStopTextualIdentifier());
        }
        else
        {
            balLayout.setVisibility(View.VISIBLE);
            srcLLayout.setVisibility(View.GONE);
            destTV.setText(AppPreferences.getAppPrefrences(VariablesConstant.CURRENT_STOP,getContext()));
            fareTV.setText(getArguments().getString("fare"));
            balance.setText(getArguments().getString("balance"));
            ticketTypeTV.setText("Value Wallet");
        }
        return view;
    }

    private void setDate(TextView dateView) {
        Date today = Calendar.getInstance().getTime();//getting date
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
        String str = formatter.format(today);
        dateView.setText(str);
    }
}