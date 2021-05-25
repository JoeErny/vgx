package com.example.vgxchange.api.controllers;

import android.util.Log;

import com.example.vgxchange.model.Category;
import com.example.vgxchange.model.Game;
import com.example.vgxchange.network.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryApiController {

    public List<Category> getAllSynchronous() {
        List<Category> categories = new ArrayList<>();

        Retrofit retrofit = ApiRetrofit.getClient();

        CategoryApi categoryApi = retrofit.create(CategoryApi.class);
        Call<List<Category>> call = categoryApi.getAll();
        try {
            Response<List<Category>> response = call.execute();
            categories = response.body();
        }
        catch (Exception x)
        {
            Log.d("Api Category Error : ", x.getMessage());
        }
        return categories;
    }
}
