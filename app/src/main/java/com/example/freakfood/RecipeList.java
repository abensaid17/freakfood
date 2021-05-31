package com.example.freakfood;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freakfood.adapters.OnRecipeListener;
import com.example.freakfood.adapters.RecipeRecyclerAdapter;
import com.example.freakfood.model.Recipe;
import com.example.freakfood.request.RecipesApi;
import com.example.freakfood.request.ServGenerator;
import com.example.freakfood.request.response.RecipeResponse;
import com.example.freakfood.request.response.SearchResponse;
import com.example.freakfood.util.Constantes;
import com.example.freakfood.util.Testing;
import com.example.freakfood.viewModel.RecipeViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeList extends BaseActivity implements OnRecipeListener {

    private static final String TAG = "RecipeList";
    private RecipeViewModel mRecipeViewModel;
    private RecyclerView mRecyclerView;
    private RecipeRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        mRecyclerView = findViewById(R.id.recipe_list);
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        initRecyclerView();
        onChangedLivedata();

    }

    private void onChangedLivedata(){
        mRecipeViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if(recipes != null){
                    for (Recipe recipe : recipes){
                        Testing.printRecipes(recipes,"recipes test");
                        mAdapter.setmRecipes(recipes);
                    }
                }
            }
        });
    }
    private void initRecyclerView(){
        mAdapter = new RecipeRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void searchRecipesApi(String query,int pageNumber){
        mRecipeViewModel.searchRecipesApi(query,pageNumber);
    }

    private void testRetrofitRequest(){
        searchRecipesApi("chicken breasts",1);
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategroyClick(String category) {

    }
}