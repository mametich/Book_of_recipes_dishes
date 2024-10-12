package com.example.burgershop.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.burgershop.model.Recipe

@Dao
interface RecipesDao {

    @Query("SELECT * FROM Recipe WHERE categoryId=:categoryId")
    suspend fun getRecipesById(categoryId: Int) : List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipes(listOfRecipes: List<Recipe>)

}