package com.example.vgxchange.fragments.menu_navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vgxchange.api.controllers.SuggestionApi;
//import com.example.vgxchange.fragments.menu_navigation.CategoryAdapter;
import com.example.vgxchange.model.CategoriesStatistics;
import com.example.vgxchange.model.GamesStatistics;
import com.example.vgxchange.R;
import com.example.vgxchange.network.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeFragment extends Fragment {

    RecyclerView recyclerViewGame;
    RecyclerView recyclerViewCategory;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.home_fragment, container, false);

        recyclerViewGame = view.findViewById(R.id.recyclerViewTopGame);
        recyclerViewCategory = view.findViewById(R.id.recyclerViewTopCategory);

        Retrofit retrofitCategory = ApiRetrofit.getClient();
        SuggestionApi suggestionApi = retrofitCategory.create(SuggestionApi.class);

        //Top catégorie
        Call<List<CategoriesStatistics>> call = suggestionApi.getSuggestionCategory();
        call.enqueue(new Callback<List<CategoriesStatistics>>() {
            @Override
            public void onResponse(Call<List<CategoriesStatistics>> call, Response<List<CategoriesStatistics>> response) {
                List<CategoriesStatistics> liste;
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        liste = response.body();
                        TopCategoryAdapter adapter = new TopCategoryAdapter(view.getContext(), liste);
                        recyclerViewCategory.setAdapter(adapter);
                        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    }
                    else{
                        Log.d("ReponseFail :", "Body is null");
                    }
                }
                else{
                    Log.d("ReponseFail :", "Response not successfull");
                }
            }

            @Override
            public void onFailure(Call<List<CategoriesStatistics>> call, Throwable t) {
                Log.d("ReponseFail", t.getMessage());
            }
        });
        //Top game
        Call<List<GamesStatistics>> call2 = suggestionApi.getSuggestionGame();
        call2.enqueue(new Callback<List<GamesStatistics>>() {
            @Override
            public void onResponse(Call<List<GamesStatistics>> call, Response<List<GamesStatistics>> response) {
                List<GamesStatistics> liste2;
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        liste2 = response.body();
                        TopGameAdapter adapter2 = new TopGameAdapter(view.getContext(), liste2);
                        recyclerViewGame.setAdapter(adapter2);
                        recyclerViewGame.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    }
                    else{
                        Log.d("ReponseFail :", "Body is null");
                    }
                }
                else{
                    Log.d("ReponseFail :", "Response not successfull");
                }
            }

            @Override
            public void onFailure(Call<List<GamesStatistics>> call, Throwable t) {
                Log.d("ReponseFail", t.getMessage());
            }
        });



        return view;
    }
}





