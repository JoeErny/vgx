package com.example.vgxchange.api.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.vgxchange.R;
import com.example.vgxchange.model.SellerProfil;
import com.example.vgxchange.model.User;
import com.example.vgxchange.network.ApiRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SellerProfilController {

    SellerProfil sellerProfil = new SellerProfil();
}
/*
    public SellerProfil getNumberOfSales(String id) {

        Retrofit retrofit = ApiRetrofit.getClient();

        SellerProfilApi sellerProfilApi = retrofit.create(SellerProfilApi.class);
        Call<SellerProfil> call = sellerProfilApi.getNumberOfSales(id);

        call.enqueue(new Callback<SellerProfil>() {
            @Override
            public void onResponse(Call<SellerProfil> call, Response<SellerProfil> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        sellerProfil = response.body();
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
            public void onFailure(Call<SellerProfil> call, Throwable t) {
                Log.d("ReponseFail", t.getMessage());
            }
        });

        return sellerProfil;
    }

    public SellerProfil getNumberOfRentals(String id) {

        Retrofit retrofit = ApiRetrofit.getClient();

        SellerProfilApi sellerProfilApi = retrofit.create(SellerProfilApi.class);
        Call<SellerProfil> call = sellerProfilApi.getNumberOfRentals(id);

        call.enqueue(new Callback<SellerProfil>() {
            @Override
            public void onResponse(Call<SellerProfil> call, Response<SellerProfil> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        sellerProfil = response.body();
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
            public void onFailure(Call<SellerProfil> call, Throwable t) {
                Log.d("ReponseFail", t.getMessage());
            }
        });

        return sellerProfil;
    }

    public SellerProfil getRating(String id) {

        Retrofit retrofit = ApiRetrofit.getClient();

        SellerProfilApi sellerProfilApi = retrofit.create(SellerProfilApi.class);
        Call<SellerProfil> call = sellerProfilApi.getRating(id);

        call.enqueue(new Callback<SellerProfil>() {
            @Override
            public void onResponse(Call<SellerProfil> call, Response<SellerProfil> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        sellerProfil = response.body();
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
            public void onFailure(Call<SellerProfil> call, Throwable t) {
                Log.d("ReponseFail", t.getMessage());
            }
        });

        return sellerProfil;
    }
}*/

    /*
    public SellerProfil getNumberOfSales(String id) {

        SellerProfil sellerProfil = new SellerProfil();

        Retrofit retrofit = ApiRetrofit.getClient();

        SellerProfilApi sellerProfilApi = retrofit.create(SellerProfilApi.class);
        Call<SellerProfil> call = sellerProfilApi.getNumberOfSales(id);

        try {
            Response<SellerProfil> response = call.execute();
            sellerProfil = response.body();
        } catch (Exception x) {
            Log.d("Api error : ", x.getMessage());
        }
        return sellerProfil;
    }

    public SellerProfil getNumberOfRentals(String id) {

        SellerProfil sellerProfil = new SellerProfil();

        Retrofit retrofit = ApiRetrofit.getClient();

        SellerProfilApi sellerProfilApi = retrofit.create(SellerProfilApi.class);
        Call<SellerProfil> call = sellerProfilApi.getNumberOfRentals(id);

        try {
            Response<SellerProfil> response = call.execute();
            sellerProfil = response.body();
        } catch (Exception x) {
            Log.d("Api error : ", x.getMessage());
        }
        return sellerProfil;
    }

    public SellerProfil getRating(String id) {

        SellerProfil sellerProfil = new SellerProfil();

        Retrofit retrofit = ApiRetrofit.getClient();

        SellerProfilApi sellerProfilApi = retrofit.create(SellerProfilApi.class);
        Call<SellerProfil> call = sellerProfilApi.getRating(id);

        try {
            Response<SellerProfil> response = call.execute();
            sellerProfil = response.body();
        } catch (Exception x) {
            Log.d("Api error : ", x.getMessage());
        }
        return sellerProfil;
    }

}
    */

