package com.example.bottomnavwfab.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bottomnavwfab.api.Repository
import com.example.bottomnavwfab.ui.home.HomeViewModel

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel() as T
    }
}