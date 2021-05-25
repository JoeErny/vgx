package com.example.vgxchange.api.controllers;

import android.util.Log;

import com.example.vgxchange.model.Game;
import com.example.vgxchange.network.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GameApiController {

    public List<Game> getAllSynchronous() {
        List<Game> games = new ArrayList<>();

        Retrofit retrofit = ApiRetrofit.getClient();

        GameApi gameApi = retrofit.create(GameApi.class);
        Call<List<Game>> call = gameApi.getAll();
        try {
            Response<List<Game>> response = call.execute();
            games = response.body();
        }
        catch (Exception x)
        {
            Log.d("Api Game Error : ", x.getMessage());
        }
        return games;
    }


}
