package com.example.vgxchange.fragments.menu_navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.vgxchange.Constants;
import com.example.vgxchange.R;
import com.example.vgxchange.tool.WhichProposalsToDisplay;

public class UserFragment extends Fragment implements View.OnClickListener {


    private View view;

    public UserFragment() {
        // Required empty public constructor
        if (getArguments() != null) {

        }
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    public void redirectToConnectionIfNotConnected(View v) {
        SharedPreferences sharedPrefReturn = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean userIsConnected = sharedPrefReturn.getBoolean("isConnected", false);

        if (userIsConnected) {

        } else {
            Navigation.findNavController(view).navigate(R.id.action_userFragment_to_connectionFragment);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        Button btn = view.findViewById(R.id.button_profil);
        btn.setOnClickListener(this::onClick);

        Button btn2 = view.findViewById(R.id.logout);
        btn2.setOnClickListener(this::onClickLogout);

        Button btn3 = view.findViewById(R.id.button_add_product_page);
        btn3.setOnClickListener(this::onClickAddProduct);

        Button btn4 = view.findViewById(R.id.button_my_Product_page);
        btn4.setOnClickListener(this::onClickMy_Product_page);

        Button goToMyProposals = view.findViewById(R.id.button_my_proposals);
        goToMyProposals.setOnClickListener(v -> {
            Bundle whichProposalsToDisplayBundle = new Bundle();
            whichProposalsToDisplayBundle.putSerializable(Constants.whichProposalsBundleKey, WhichProposalsToDisplay.sent);
            Navigation.findNavController(v).navigate(R.id.action_userFragment_to_usersPropositionsFragment, whichProposalsToDisplayBundle);
        });
        Button goToReceivedProposals = view.findViewById(R.id.received_propositions);
        goToReceivedProposals.setOnClickListener(v -> {
            Bundle whichProposalsToDisplayBundle = new Bundle();
            whichProposalsToDisplayBundle.putSerializable(Constants.whichProposalsBundleKey, WhichProposalsToDisplay.received);
            Navigation.findNavController(v).navigate(R.id.action_userFragment_to_usersPropositionsFragment, whichProposalsToDisplayBundle);
        });


        return view;
    }

    private void onClickAddProduct(View view) {
        Navigation.findNavController(view).navigate(R.id.action_userFragment_to_addProductFragment);
    }


    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        redirectToConnectionIfNotConnected(view);
    }

    @Override
    public void onClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_userFragment_to_profilFragment);
    }

    public void onClickLogout(View View) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isConnected", false);
        editor.putString("userId", "");
        editor.putString("userName", "");
        editor.putString("userFirstName", "");
        editor.putString("userMail", "");
        editor.putString("userTelephone", "");
        editor.putString("userLogin", "");
        editor.commit();

        Navigation.findNavController(view).navigate(R.id.action_userFragment_to_connectionFragment);
    }

    public void onClickMy_Product_page(View View) {
        Navigation.findNavController(view).navigate(R.id.action_userFragment_to_myProductFragment);
    }


}




