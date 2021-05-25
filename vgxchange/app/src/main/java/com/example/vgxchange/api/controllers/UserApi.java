package com.example.vgxchange.api.controllers;

import com.example.vgxchange.api.dto.UserAuthentification;
import com.example.vgxchange.api.dto.UserSubscription;
import com.example.vgxchange.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @GET("/api/user/3eec32ca-8b0f-496e-b63b-ca2f7819630e")
    Call<User> getUser();

    @POST("/api/register")
    Call<UserSubscription> postUserSubscription(@Body UserSubscription userSubscription);

    @POST("/api/connection")
    Call<User> postUserConnection(@Body UserAuthentification userAuthentification);

    @GET("/api/ResetPassword/{login}")
    Call<String> getResetPassword(@Path("login") String login );


}
