package com.example.bottomnavwfab.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Recipe::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}