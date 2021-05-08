package com.nextgentele.busvalidatorv2.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.nextgentele.busvalidatorv2.R;
import com.nextgentele.busvalidatorv2.db.Users;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardExitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardExitFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Users users;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CardExitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardExitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardExitFragment newInstance(String param1, String param2) {
        CardExitFragment fragment = new CardExitFragment();
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
        View view= inflater.inflate(R.layout.fragment_card_exit, container, false);
        users = (Users) getArguments().getSerializable("users");
        TextView cardBalance = view.findViewById(R.id.aCardBal_activity_second);
        TextView fareTV=view.findViewById(R.id.fareDeduct_activity_second);
        TextView availableBal=view.findViewById(R.id.newBalance_activity_second);
        float available=users.getUserBal();
        float fare=users.getFare();
        float bal=available-fare;

        cardBalance.setText(String.valueOf(available));
        fareTV.setText(String.valueOf(fare));
        availableBal.setText(String.valueOf(bal));

        return view;
    }
    private void setDate(TextView dateView) {
        Date today = Calendar.getInstance().getTime();//getting date
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
        String str = formatter.format(today);
        dateView.setText(str);
    }
}