package com.example.bottomnavwfab.ui.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bottomnavwfab.api.Repository
import com.example.bottomnavwfab.data.RecipeData
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var liveDataList: MutableLiveData<ArrayList<RecipeData>> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<ArrayList<RecipeData>> {
        return liveDataList
    }

    fun callGenerateList(ingredients: String) {
        Log.e("homeviewmodel", ingredients)
        val recipesList: Call<ArrayList<RecipeData>> =
            repository.getInfo("057efdc3940f476286fccda0ea8957e1", ingredients, 5)

        recipesList.enqueue(object : Callback<ArrayList<RecipeData>> {

            override fun onResponse(
                call: Call<ArrayList<RecipeData>>,
                response: Response<ArrayList<RecipeData>>
            ) {

                liveDataList.postValue(response.body())

                Log.e("recipesList", recipesList.toString())

            }

            override fun onFailure(call: Call<ArrayList<RecipeData>>, t: Throwable) {
                Log.i(ContentValues.TAG, "onFailure: something went wrong")
            }
        })
    }
}