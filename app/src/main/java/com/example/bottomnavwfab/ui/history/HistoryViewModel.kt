package com.example.bottomnavwfab.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bottomnavwfab.database.Recipe
import com.example.bottomnavwfab.database.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {
    val allRecipes: LiveData<List<Recipe>> = repository.allRecipes

    fun getLiveDataObserver(): LiveData<List<Recipe>> {
        return allRecipes
    }

    fun getAll(): LiveData<List<Recipe>> {
        return repository.allRecipes
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

    // setting up coroutine to run repository function findByTitle on the IO thread
    fun findByTitle(title: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.findByTitle(title)
    }
}

class RecipeViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}