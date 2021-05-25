package com.example.vgxchange.fragments.user_management;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.vgxchange.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ProfilFragment extends Fragment {

    SharedPreferences sharedPrefReturn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment thirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
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
        View view = inflater.inflate(R.layout.profile_page, container, false);
        sharedPrefReturn = getActivity().getPreferences(Context.MODE_PRIVATE);

        TextView textViewFirstName;
        TextView textViewMail;
        TextView textViewName;
        TextView textViewLogin;
        TextView textViewTelephone;

        textViewFirstName = view.findViewById(R.id.textViewFirstName);
        textViewMail = view.findViewById(R.id.textviewMail);
        textViewName = view.findViewById(R.id.textViewName);
        textViewLogin = view.findViewById(R.id.textViewLogin);
        textViewTelephone = view.findViewById(R.id.textViewTelephone);


        textViewFirstName.setText(sharedPrefReturn.getString("userFirstName", "error"));
        textViewMail.setText(sharedPrefReturn.getString("userMail", "error"));
        textViewName.setText(sharedPrefReturn.getString("userName", "error"));
        textViewLogin.setText(sharedPrefReturn.getString("userLogin", "error"));
        textViewTelephone.setText(sharedPrefReturn.getString("userTelephone", "error"));

        return view;


    }
}