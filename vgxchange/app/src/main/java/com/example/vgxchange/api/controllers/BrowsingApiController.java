package com.example.vgxchange.api.controllers;

import android.util.Log;

import com.example.vgxchange.model.ProductAnnounce;
import com.example.vgxchange.network.ApiRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BrowsingApiController {

    public List<ProductAnnounce> getAllSynchronous() {
        List<ProductAnnounce> productAnnounces = new ArrayList<>();
        Retrofit retrofit = ApiRetrofit.getClient();

        BrowsingApi browsingApi = retrofit.create(BrowsingApi.class);
        Call<List<ProductAnnounce>> call = browsingApi.getAllProducts();
        try {
            Response<List<ProductAnnounce>> response = call.execute();
            productAnnounces = response.body();
        } catch (Exception x) {
            Log.d("Api Product Error : ", x.getMessage());
        }
        return productAnnounces;
    }


    public List<ProductAnnounce> getByCategorySynchronous(String categoryId) {
        List<ProductAnnounce> productAnnounces = new ArrayList<>();
        Retrofit retrofit = ApiRetrofit.getClient();

        BrowsingApi browsingApi = retrofit.create(BrowsingApi.class);
        Call<List<ProductAnnounce>> call = browsingApi.getAllProductsByCategory(categoryId);
        try {
            Response<List<ProductAnnounce>> response = call.execute();
            productAnnounces = response.body();
        } catch (Exception x) {
            Log.d("Api Product Error : ", x.getMessage());
        }
        return productAnnounces;
    }

    public List<ProductAnnounce> getProductByUser(String idUser, int status)
    {
        List<ProductAnnounce> productAnnounces = new ArrayList<>();
        Retrofit retrofit = ApiRetrofit.getClient();
        BrowsingApi browsingApi = retrofit.create(BrowsingApi.class);
        Call<List<ProductAnnounce>> call = browsingApi.getProductByUser(idUser, status);
        try {
            Response<List<ProductAnnounce>> response = call.execute();
            productAnnounces = response.body();
        } catch (Exception x) {
            Log.d("Api Product Error : ", x.getMessage());
        }
        return productAnnounces;
    }
}
