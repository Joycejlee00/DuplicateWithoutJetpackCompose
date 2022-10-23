package com.example.bottomnavwfab.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bottomnavwfab.api.Repository
import com.example.bottomnavwfab.database.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val allRecipes: LiveData<List<Recipe>> = repository.allRecipes

    fun getLiveDataObserver(): LiveData<List<Recipe>> {
        return allRecipes
    }

    // setting up coroutine to run repository function delete on the IO thread
    fun deleteRecipe(title: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(title)
    }

    // setting up coroutine to run repository function insert on the IO thread
    fun addRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(recipe)
    }

    // setting up coroutine to run repository function deleteAll on the IO thread
    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    // check is the database is empty
    fun isEmpty(): Boolean {
        return repository.isEmpty()
    }
}