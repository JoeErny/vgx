package com.example.vgxchange.api.controllers;

import com.example.vgxchange.api.dto.ProductToCreate;
import com.example.vgxchange.model.ProductAnnounce;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProductApi {
    @GET("/api/product/{id}")
    Call<ProductAnnounce> getProduct();

    @POST("/api/product")
    Call<ProductAnnounce> postProduct(@Body ProductAnnounce product);
    
    @POST("/api/addProduct")
    Call<ProductToCreate> postProduct(@Body ProductToCreate productToCreate);

    @DELETE("/api/productToDelete/{id}")
    Call<String> deleteProduct(@Body String id);

}
