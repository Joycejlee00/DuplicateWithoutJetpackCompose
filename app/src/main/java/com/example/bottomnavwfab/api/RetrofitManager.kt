package com.example.bottomnavwfab.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {

    private const val BASE_URL = "https://api.spoonacular.com/"

    fun getRetrofitInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}