package com.example.bottomnavwfab.database


import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipe_table")
    fun getAll(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipe_table WHERE title LIKE :title")
    fun findByTitle(title: String): Recipe

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: Recipe)

    @Query("DELETE FROM recipe_table WHERE title LIKE:title")
    fun delete(title: String)

    @Query("DELETE FROM recipe_table")
    fun deleteAll()

    @Query("SELECT (SELECT COUNT(*) FROM recipe_table) == 0")
    fun isEmpty(): Boolean
}