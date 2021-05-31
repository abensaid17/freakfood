package com.example.freakfood.request;

import com.example.freakfood.request.response.RecipeResponse;
import com.example.freakfood.request.response.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipesApi {
    @GET("api/search")
    Call<SearchResponse> searchRecipe(
            @Query("key")String key,
            @Query("q")String query,
            @Query("page")String page
    );

    @GET("api/get")
    Call<RecipeResponse> getRecipe(
            @Query("key") String key,
            @Query("rId") String recipe_id
    );
}
