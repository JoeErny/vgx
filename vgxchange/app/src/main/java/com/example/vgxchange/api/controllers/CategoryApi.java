package com.example.vgxchange.api.controllers;

import com.example.vgxchange.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryApi {

    @GET("/api/category")
    Call<List<Category>> getAll();
}
