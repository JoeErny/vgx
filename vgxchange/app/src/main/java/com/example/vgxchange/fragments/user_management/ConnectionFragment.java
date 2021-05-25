package com.example.vgxchange.fragments.user_management;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.vgxchange.R;
import com.example.vgxchange.api.controllers.UserApi;
import com.example.vgxchange.api.dto.UserAuthentification;
import com.example.vgxchange.model.User;
import com.example.vgxchange.network.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConnectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConnectionFragment extends Fragment {

    EditText login;
    EditText password;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnResetPwd;
    private Button btnSubscription;

    public ConnectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConnectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConnectionFragment newInstance(String param1, String param2) {
        ConnectionFragment fragment = new ConnectionFragment();
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
        View view = inflater.inflate(R.layout.fragment_connection, container, false);

        btnResetPwd = view.findViewById(R.id.button_resetPassword);
        btnResetPwd.setOnClickListener(this::onClickResetPassword);

        btnSubscription = view.findViewById(R.id.button_subscription);
        btnSubscription.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_connectionFragment_to_subscriptionFragment);
        });


        login = view.findViewById(R.id.loginEditText);
        password = view.findViewById(R.id.passwordEditText);
        Button btnConnect = view.findViewById(R.id.button_connection);
        btnConnect.setOnClickListener(this::onClickConnection);

        Log.d("onCreateView", "onCreateView");


        return view;
    }

    public void onClickConnection(View view) {
        String loginTxt = String.valueOf(login.getText());
        String passwordTxt = String.valueOf(password.getText());

        if (loginTxt.matches("") || passwordTxt.matches("")) {
            Toast.makeText(getContext(), "Veuillez remplir tous les champs !", Toast.LENGTH_SHORT).show();
            return;
        }

        UserAuthentification userAuthentification = new UserAuthentification(loginTxt, passwordTxt);

        Retrofit retrofit = ApiRetrofit.getClient();
        UserApi userApi = retrofit.create(UserApi.class);

        Call<User> call = userApi.postUserConnection(userAuthentification);

        Context context = getContext();
        int duration = Toast.LENGTH_SHORT;

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("Reponse", String.valueOf(response.code()));

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //login / password is correct
                        Log.d("Reponse", "login / password is correct");
                        CharSequence text = "Connexion Réussie";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        /***** SESSION *****/

                        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("isConnected", true);

                        User newUser = new User(response.body().getId(), response.body().getFirstname(), response.body().getName(), response.body().getMail(),
                                response.body().login, response.body().getTelephone(), response.body().getPassword());

                        editor.putBoolean("isConnected", true);
                        editor.putString("userId", newUser.id);
                        editor.putString("userName", newUser.name);
                        editor.putString("userFirstName", newUser.firstname);
                        editor.putString("userMail", newUser.mail);
                        editor.putString("userTelephone", newUser.telephone);
                        editor.putString("userLogin", newUser.login);

                        //editor.putString("idUtilisateur","testGuido");
                        editor.commit();
                        //Connexion réussie redirection Profil fragment

                        Navigation.findNavController(view).navigate(R.id.action_connectionFragment_to_userFragment);
                    } else {
                        //login / password is WRONG
                        Log.d("Reponse", "login / password is WRONG");

                        CharSequence text = "Mauvaise combinaison";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("ReponseFail", t.getMessage());
                CharSequence text = "Erreur Serveur";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    public void onClickRedirectToSubscription(View view) {

        if(view == null)
        {
            Toast.makeText(getContext(), "VIEW NULL", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getContext(), "VIEW NOT NULL"+view.findViewById(R.id.action_connectionFragment_to_subscriptionFragment).toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onClickResetPassword(View view) {
        String loginTxt = String.valueOf(login.getText());

        if (loginTxt.matches("")) {
            Toast.makeText(getContext(), "Veuillez remplir votre login !", Toast.LENGTH_SHORT).show();
            return;

        } else {

            Retrofit retrofit = ApiRetrofit.getClient();
            UserApi userApi = retrofit.create(UserApi.class);

            Call<String> call = userApi.getResetPassword(loginTxt);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.d("Reponse", String.valueOf(response.code()));
                    Toast.makeText(getContext(), response.body(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("ReponseFail", t.getMessage());
                    Toast.makeText(getContext(), "erreur Serveur", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}

