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
import com.nextgentele.busvalidatorv2.presenter.EntryManager;
import com.nextgentele.busvalidatorv2.roomdb.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QREntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QREntryFragment extends Fragment {

    AppDatabase appDatabase;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QREntryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QRFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QREntryFragment newInstance(String param1, String param2) {
        QREntryFragment fragment = new QREntryFragment();
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EntryManager entryManager = new EntryManager(getContext());

        View view = inflater.inflate(R.layout.fragment_q_r, container, false);
//        appDatabase=AppDatabase.getAppDatabase(getActivity());
        ModelListActiveSjtTicketPayload models = (ModelListActiveSjtTicketPayload) getArguments().getSerializable("qrTicket");

        TextView balance = view.findViewById(R.id.bal_amout_fragment_qr);
        LinearLayout destinationLayout = view.findViewById(R.id.dest_ll);
        LinearLayout fareLayout = view.findViewById(R.id.fare_ll);
        LinearLayout balLayout = view.findViewById(R.id.bal_ll);
        TextView ticketTypeTV = view.findViewById(R.id.ticket_type_fragment_qr);

        TextView srcTV = view.findViewById(R.id.src_stop_fragment_qr);
        TextView destTV = view.findViewById(R.id.dest_stop_fragment_qr);
        TextView fareTV = view.findViewById(R.id.fare_amout_fragment_qr);
        TextView dateTV = view.findViewById(R.id.date_fragment_qr);
        setDate(dateTV);
        srcTV.setText(AppPreferences.getAppPrefrences(VariablesConstant.CURRENT_STOP,getContext()));

        if (models!=null) {
            entryManager.updateEntry(models);
            destTV.setText(models.getDestStopTextualIdentifier());
            fareTV.setText(models.getFare());

        }
        else
        {
            balLayout.setVisibility(View.VISIBLE);
            fareLayout.setVisibility(View.GONE);
            destinationLayout.setVisibility(View.GONE);
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