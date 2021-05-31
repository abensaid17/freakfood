package com.example.freakfood.request;

import com.example.freakfood.util.Constantes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServGenerator {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constantes.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RecipesApi recipeApi = retrofit.create(RecipesApi.class);

    public static RecipesApi getRecipeApi(){
        return recipeApi;
    }
}
