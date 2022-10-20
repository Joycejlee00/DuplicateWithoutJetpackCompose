package com.example.bottomnavwfab.ui.home

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
class HomeViewModel @Inject constructor(): ViewModel() {

    val repository = Repository()

    var liveDataList: MutableLiveData<ArrayList<RecipeData>>

    init {
        liveDataList = MutableLiveData()
    }

    fun getLiveDataObserver(): MutableLiveData<ArrayList<RecipeData>> {
        return liveDataList
    }

    fun callGenerateList(ingredients: String) {
        Log.e("homeviewmodel", ingredients)
        val recipesList: Call<ArrayList<RecipeData>> =
            //dont hardcode the strings
            repository.getInfo("18d08d02d1eb4bc6a9bd56fb36f4cf24",ingredients, 5)

        recipesList.enqueue(object : Callback<ArrayList<RecipeData>> {

            override fun onResponse(
                call: Call<ArrayList<RecipeData>>,
                response: Response<ArrayList<RecipeData>>
            ) {


                //liveDataList.postValue(response.body())
                //               generateDataList(response)
                liveDataList.postValue(response.body())

                Log.e("recipesList", recipesList.toString())

            }


            override fun onFailure(call: Call<ArrayList<RecipeData>>, t: Throwable) {
//                Toast.makeText(this@HomeFragment, "Something went wrong", Toast.LENGTH_SHORT)
//                    .show()
            }
        })

    }
}