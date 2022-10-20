package com.example.bottomnavwfab.api
import com.example.bottomnavwfab.data.RecipeData
import retrofit2.Call
import retrofit2.http.*

interface SimpleApi {

    //QUERY parameters to set endpoints
    @GET("recipes/findByIngredients")
    fun getAllRecipes(
        @Query("apiKey") apiKey: String,
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int
    ): Call<ArrayList<RecipeData>>

}