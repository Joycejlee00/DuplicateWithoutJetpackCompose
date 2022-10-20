package com.example.bottomnavwfab.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val recipeDao: RecipeDao) {

    // Room DB -> DAO -> Repository -> ViewModel -> View: Access using viewModel on Fragment
    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAll()

    @WorkerThread
    fun findByTitle(title: String): Recipe {
        return recipeDao.findByTitle(title)
    }

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
}