package com.example.bottomnavwfab.api

import com.example.bottomnavwfab.data.RecipeData
import retrofit2.Call

class Repository {
     fun getInfo(apiKey: String, ingredient: String, number: Int): Call<ArrayList<RecipeData>> {
        return RetrofitManager.getRetrofitInstance().create(SimpleApi::class.java).getAllRecipes(apiKey, ingredient, number)
    }
}