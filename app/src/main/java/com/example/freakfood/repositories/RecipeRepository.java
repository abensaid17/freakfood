package com.example.freakfood.repositories;

import androidx.lifecycle.LiveData;

import com.example.freakfood.model.Recipe;
import com.example.freakfood.request.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository repoInstance;
    private RecipeApiClient mRecipeApiClient;

    public static RecipeRepository getInstance(){
        if(repoInstance == null){
            repoInstance = new RecipeRepository();
        }
        return repoInstance;
    }

    private RecipeRepository(){
        mRecipeApiClient = RecipeApiClient.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipeApiClient.getRecipes();
    }


    public void searchRecipesApi(String query,int pageNumber){
        mRecipeApiClient.SearchRecipeApi(query,pageNumber);
    }
}
