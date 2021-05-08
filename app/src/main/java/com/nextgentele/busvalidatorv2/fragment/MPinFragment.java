package com.nextgentele.busvalidatorv2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.nextgentele.busvalidatorv2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MPinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MPinFragment extends Fragment {

    RadioGroup rg;
    EditText mobileET, mpinET;
    Button btn;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MPinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MPinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MPinFragment newInstance(String param1, String param2) {
        MPinFragment fragment = new MPinFragment();
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
        View view= inflater.inflate(R.layout.fragment_m_pin, container, false);

        rg=view.findViewById(R.id.rdgroup_mpin_frgm);
        mobileET=view.findViewById(R.id.id_input_mpin_frgm);
        mpinET=view.findViewById(R.id.mpin_input_mpin_frgm);
        btn=view.findViewById(R.id.loginbtn_id_frgm);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(isvalidation()){
//                    Intent intent = new Intent(getActivity(), RouteActivity.class);
//                    startActivity(intent);
//                }

            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (i==R.id.customerid_radio_mpin_frgm){
                    mobileET.setHint("Customer ID");
                }else
                {
                    mobileET.setHint("Mobile Number");
                }
            }
        });



        return view;
    }

    private boolean isvalidation() {

        if (mobileET.getText().toString().length() == 0) {
            mobileET.setError("Please Enter the mobile / Customer ID");
            return false;
        } else if (mpinET.length() == 0) {
            mpinET.setError("Please Enter the Mpin");
            return false;
        }
        return true;
    }
}