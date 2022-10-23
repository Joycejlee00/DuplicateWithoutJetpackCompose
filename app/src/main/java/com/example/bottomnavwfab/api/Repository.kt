package com.example.bottomnavwfab.api

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.bottomnavwfab.data.RecipeData
import com.example.bottomnavwfab.database.Recipe
import com.example.bottomnavwfab.database.RecipeDao
import retrofit2.Call
import javax.inject.Inject

/**
 * Repository class grabs a retrofit Instance and gets the GET call from the GetDataService Interface class
 * It then grabs the said function that then passes in the APIKey, Ingredient, and number. This class is then
 * called in the MainViewModel class when entering our APIKey and such
 */
class Repository @Inject constructor(private val recipeDao: RecipeDao) {

    // Room DB -> DAO -> Repository -> ViewModel -> View: Access using viewModel on Fragment
    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAll()

    @WorkerThread
    fun insert(recipe: Recipe) {
        recipeDao.insert(recipe)
    }

    @WorkerThread
    fun delete(title: String) {
        recipeDao.delete(title)
    }

    @WorkerThread
    fun deleteAll() {
        recipeDao.deleteAll()
    }

    @WorkerThread
    fun isEmpty(): Boolean {
        return recipeDao.isEmpty()
    }

    fun getInfo(apiKey: String, ingredient: String, number: Int): Call<ArrayList<RecipeData>> {
        Log.i(
            TAG,
            "url is : " + RetrofitManager.getRetrofitInstance().create(GetDataService::class.java)
                .getAllRecipes(apiKey, ingredient, number).request().url().url().toString()
        )
        return RetrofitManager.getRetrofitInstance().create(GetDataService::class.java)
            .getAllRecipes(apiKey, ingredient, number)
    }

    companion object {
        var TAG: String = Repository::class.java.simpleName
    }
}