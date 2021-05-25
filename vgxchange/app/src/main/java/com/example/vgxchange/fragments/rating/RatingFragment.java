package com.example.vgxchange.fragments.rating;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.vgxchange.Constants;
import com.example.vgxchange.R;
import com.example.vgxchange.api.controllers.RatingApi;
import com.example.vgxchange.api.dto.Evaluation;
import com.example.vgxchange.model.BuyingProp;
import com.example.vgxchange.network.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RatingFragment extends Fragment {
    BuyingProp proposalToShow;

    SharedPreferences sharedPrefReturn;
    String idRatingProduct ;
    String idRatingUser ;
    RatingBar rating ;
    EditText comment;

    public RatingFragment() {
        // Required empty public constructor
    }

    public static RatingFragment newInstance(String param1, String param2) {
        RatingFragment fragment = new RatingFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);
        sharedPrefReturn = getActivity().getPreferences(Context.MODE_PRIVATE);

        rating = view.findViewById(R.id.ratingBar);
        comment = view.findViewById(R.id.editTextCommentRating);
        rating.setRating(1);

        Button btn = view.findViewById(R.id.buttonRating);
        btn.setOnClickListener(v -> { onClickRate(view); });

        if (getArguments() != null)
        {
            proposalToShow = (BuyingProp) getArguments().getSerializable(Constants.selectedBuyingProposalBundleKey);
        }
        return view;
    }

    private void onClickRate(View view) {

        idRatingProduct = proposalToShow.id;
        idRatingUser = sharedPrefReturn.getString("userId", "error id");

        Retrofit retrofit = ApiRetrofit.getClient();
        RatingApi ratingApi = retrofit.create(RatingApi.class);
        if (String.valueOf(comment.getText()).matches("")) {
            Toast.makeText(getContext(), "Veuillez remplir tout les champs !", Toast.LENGTH_SHORT).show();
            return;

        } else {

            Evaluation ratingToAdd = new Evaluation(idRatingUser, idRatingProduct, rating.getNumStars(), String.valueOf(comment.getText()));
            Call<Evaluation> call = ratingApi.postRating(ratingToAdd);

            call.enqueue(new Callback<Evaluation>() {
                @Override
                public void onResponse(Call<Evaluation> call, Response<Evaluation> response) {
                    Log.d("Reponse", String.valueOf(response.code()));

                    if(response.body() != null)
                    {
                        Toast toast = Toast.makeText(getContext(), "Evaluation Envoyé", Toast.LENGTH_SHORT);
                        toast.show();
                        Navigation.findNavController(view).navigate(R.id.action_ratingFragment_to_usersPropositionsFragment);

                    }
                    else
                    {
                        Toast toast = Toast.makeText(getContext(), "Echec de l'envoi", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                @Override
                public void onFailure(Call<Evaluation> call, Throwable t) {
                    Log.d("Reponse", t.getMessage());
                    Toast toast = Toast.makeText(getContext(), "Erreur Serveur", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    };
}