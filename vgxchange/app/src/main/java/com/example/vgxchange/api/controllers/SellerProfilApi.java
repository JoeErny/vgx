package com.example.vgxchange.api.controllers;

import com.example.vgxchange.model.SellerProfil;
import com.example.vgxchange.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SellerProfilApi {

    @GET("/api/sellerstatistic/numberofsales/{id}")
    Call<Integer> getNumberOfSales(@Path("id") String id);

    @GET("/api/sellerstatistic/numberofrentals/{id}")
    Call<Integer> getNumberOfRentals(@Path("id") String id);

    @GET("/api/sellerstatistic/rating/{id}")
    Call<Integer> getRating(@Path("id") String id);

}
