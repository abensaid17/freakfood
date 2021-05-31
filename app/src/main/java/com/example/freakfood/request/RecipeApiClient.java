package com.example.freakfood.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.freakfood.AppExecutors;
import com.example.freakfood.model.Recipe;
import com.example.freakfood.request.response.RecipeResponse;
import com.example.freakfood.request.response.SearchResponse;
import com.example.freakfood.util.Constantes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class RecipeApiClient {
    private static final String TAG = "RecipeApiClient";

    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> mRecipes;
    private RetrieveRecipesRunnable mRetrieveRecipesRunnable;

    public static RecipeApiClient getInstance(){
        if(instance == null){
            instance = new RecipeApiClient();
        }
        return instance;
    }

    private RecipeApiClient(){
        mRecipes = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }

    public void SearchRecipeApi(String query,int pageNumber){
        if(mRetrieveRecipesRunnable != null){
            mRetrieveRecipesRunnable = null;
        }
        mRetrieveRecipesRunnable = new RetrieveRecipesRunnable(query,pageNumber);
        final Future handler = AppExecutors.getInstance().getmNetworkIO().submit(mRetrieveRecipesRunnable);

        AppExecutors.getInstance().getmNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //let the user know it's time out
                handler.cancel(true);
            }
        }, Constantes.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveRecipesRunnable implements Runnable{
        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveRecipesRunnable(String query,int pageNumber){
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(query,pageNumber).execute();
                if(response.code() == 200){
                   List<Recipe> list = new ArrayList<>(((SearchResponse) response.body()).getRecipes());
                   if(pageNumber == 1){
                        mRecipes.postValue(list);
                    }else{
                       List<Recipe> currentRecipes = mRecipes.getValue();
                       currentRecipes.addAll(list);
                       mRecipes.postValue(currentRecipes);
                   }
                }else{
                    String error = response.errorBody().string();
                    Log.i(TAG, "run: " + error);
                    mRecipes.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mRecipes.postValue(null);
            }

            if(cancelRequest){
                return;
            }
        }

        private Call<SearchResponse> getRecipes(String query, int pageNumber){
            return ServGenerator.getRecipeApi().searchRecipe(
                    Constantes.BASE_URL,
                    query,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: Canceling the search request.");
            cancelRequest = true;
        }
    }


}
