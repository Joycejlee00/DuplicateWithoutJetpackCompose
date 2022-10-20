package com.example.bottomnavwfab.database


import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class Recipe (
    @PrimaryKey @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "ingredients") val ingredients: String,
    @ColumnInfo(name = "image") val image: String?,
    var expandable : Boolean = false
)