package com.example.freakfood.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.freakfood.model.Recipe;
import com.example.freakfood.repositories.RecipeRepository;


import java.util.List;

public class RecipeViewModel extends ViewModel {

    private RecipeRepository mRecipeRepo;

    public RecipeViewModel() {
        mRecipeRepo = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
       return mRecipeRepo.getRecipes();
    }
    public void searchRecipesApi(String query,int pageNumber){
        mRecipeRepo.searchRecipesApi(query,pageNumber);
    }
}
