package com.example.freakfood.util;

import android.util.Log;

import com.example.freakfood.model.Recipe;

import java.util.List;

public class Testing {

    public static void printRecipes(List<Recipe> recipes,String tag){
        for (Recipe recipe: recipes) {
            Log.d(tag, "onChanged: " + recipe.getTitle());
        }
    }
}
