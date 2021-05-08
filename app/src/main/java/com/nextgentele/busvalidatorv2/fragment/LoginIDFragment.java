package com.nextgentele.busvalidatorv2.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nextgentele.busvalidatorv2.R;
import com.nextgentele.busvalidatorv2.util.ConnectionDetector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginIDFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginIDFragment extends Fragment {

    EditText loginIDET, passET;
    Button btn;

 LoginIdListener loginIdListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginIDFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginIDFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginIDFragment newInstance(String param1, String param2) {
        LoginIDFragment fragment = new LoginIDFragment();
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
        ConnectionDetector cd=new ConnectionDetector(getContext());
        View view=inflater.inflate(R.layout.fragment_login_i_d, container, false);
        loginIDET=view.findViewById(R.id.loginid_input_id_frgm);
        passET=view.findViewById(R.id.password_input_id_frgm);
        btn=view.findViewById(R.id.loginbtn_id_frgm);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isvalidation()){
                    if (cd.isConnectingToInternet()){
                    loginIdListener = (LoginIdListener) getActivity();
                    assert loginIdListener != null;
                    loginIdListener.onbuttonLogin(loginIDET.getText().toString(),passET.getText().toString());
                }else
                        Toast.makeText(getActivity(), "No InternetConnectin", Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    private boolean isvalidation() {

        if (loginIDET.getText().toString().length() == 0 || loginIDET.getText().length()!=10) {
            loginIDET.setError("Please Enter the correct Login ID");
            loginIDET.setFocusable(true);
            return false;
        } else if (passET.length() == 0) {
            passET.setError("Please Enter the Password");
            passET.setFocusable(true);
            return false;
        }else
        return true;
    }

   public interface LoginIdListener {
        public void onbuttonLogin(String mobile, String pass);
    }

}