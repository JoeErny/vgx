package com.example.vgxchange.api.controllers;

import com.example.vgxchange.model.CategoriesStatistics;
import com.example.vgxchange.model.Category;
import com.example.vgxchange.model.GamesStatistics;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SuggestionApi {

    @GET("/api/suggestion/categorystatistics")
    Call<List<CategoriesStatistics>> getSuggestionCategory();

    @GET("/api/suggestion/sellingstatistics")
    Call<List<GamesStatistics>> getSuggestionGame();

}
