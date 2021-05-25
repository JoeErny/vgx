package com.example.vgxchange.api.controllers;

import com.example.vgxchange.model.ProductAnnounce;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BrowsingApi {

    @GET("/api/browsing/all")
    Call<List<ProductAnnounce>> getAllProducts();

    @GET("/api/browsing/bycategory/{idCategory}")
    Call<List<ProductAnnounce>> getAllProductsByCategory(@Path("idCategory") String idCategory );

    @GET("/api/browsing/user/{idUser}")
    Call<List<ProductAnnounce>>getProductByUser(@Path("idUser") String idUser, @Query("status") int status);

}
