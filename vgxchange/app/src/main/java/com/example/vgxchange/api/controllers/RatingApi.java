package com.example.vgxchange.api.controllers;

import com.example.vgxchange.api.dto.Evaluation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RatingApi {

    @GET("/api/evaluation/{id}")
    Call<Evaluation> getRating();

    @POST("/api/evaluation")
    Call<Evaluation> postRating(@Body Evaluation rating);
}
