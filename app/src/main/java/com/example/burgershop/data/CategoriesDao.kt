package com.example.burgershop.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.burgershop.model.Category

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM Category")
    suspend fun getAllCategories() : List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(listOfCategory: List<Category>)

}