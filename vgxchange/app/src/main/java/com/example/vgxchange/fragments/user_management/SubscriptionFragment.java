package com.example.vgxchange.fragments.user_management;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.vgxchange.R;
import com.example.vgxchange.network.ApiRetrofit;
import com.example.vgxchange.api.controllers.UserApi;
import com.example.vgxchange.api.dto.UserSubscription;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubscriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubscriptionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText name ;
    EditText firstname ;
    EditText mail ;
    EditText login;
    EditText phone;
    EditText password;


    public SubscriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubscriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubscriptionFragment newInstance(String param1, String param2) {
        SubscriptionFragment fragment = new SubscriptionFragment();
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
        View view = inflater.inflate(R.layout.fragment_subscription, container, false);

        Button btn = view.findViewById(R.id.registration_button);
        btn.setOnClickListener(this::onClickSubscription);

        name = view.findViewById(R.id.name);
        firstname = view.findViewById(R.id.firstname);
        mail = view.findViewById(R.id.mail);
        login = view.findViewById(R.id.pseudo);
        phone = view.findViewById(R.id.phone);
        password = view.findViewById(R.id.password);

        Log.d("onCreateView", "onCreateView");
        return view;
    }


    public void onClickSubscription(View view)
    {

        String nameTxt = String.valueOf(name.getText());
        String firstnameTxt = String.valueOf(firstname.getText());
        String mailTxt = String.valueOf(mail.getText());
        String loginTxt = String.valueOf(login.getText());
        String phoneTxt = String.valueOf(phone.getText());
        String passwordTxt = String.valueOf(password.getText());

        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(mailTxt);
        boolean isValidMail = matcher.find();

        if (nameTxt.matches("") || firstnameTxt.matches("") || mailTxt.matches("") || loginTxt.matches("") || phoneTxt.matches("") || passwordTxt.matches("")) {
            Toast.makeText(getContext(), "Veuillez remplir tous les champs !", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!isValidMail){
            Toast.makeText(getContext(), "Veuillez entrer une adresse email valide !", Toast.LENGTH_SHORT).show();
            return;
        }

        //8 caractères au moins, dont une maj, une min, un chiffre et un car spécial
        Pattern VALID_PASSWORD = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        Matcher matcherPassword = VALID_PASSWORD.matcher(passwordTxt);
        boolean isValidPassword = matcherPassword.find();
        if(!isValidPassword)
        {
            Toast.makeText(getContext(), "Veuillez entrer un mot de passe fort (8 caractères minimum dont 1 min/maj, 1 chiffre, et 1 caractère spécial) !", Toast.LENGTH_SHORT).show();
            return;
        }

        UserSubscription userSubscription = new UserSubscription(nameTxt, firstnameTxt, mailTxt, loginTxt, phoneTxt, passwordTxt);

        //send http
        Retrofit retrofit = ApiRetrofit.getClient();
        UserApi userApi = retrofit.create(UserApi.class);

        Context context = getContext();
        int duration = Toast.LENGTH_SHORT;

        Call<UserSubscription> call = userApi.postUserSubscription(userSubscription);



        call.enqueue(new Callback<UserSubscription>() {
            @Override
            public void onResponse(Call<UserSubscription> call, Response<UserSubscription> response) {
                Log.d("Reponse", String.valueOf(response.code()));

                if(response.body() != null)
                {
                    CharSequence text = "Inscription réussie";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
                else
                {
                    CharSequence text = "Inscription échouée (le login est déjà utilisé par un autre utilisateur)";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


            }
            @Override
            public void onFailure(Call<UserSubscription> call, Throwable t) {
                Log.d("Reponse", t.getMessage());

                CharSequence text = "Erreur Serveur";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });
    }
}