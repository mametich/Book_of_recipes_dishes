package com.example.burgershop.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.burgershop.model.Recipe

@Dao
interface RecipesDao {

    @Query("SELECT * FROM Recipe WHERE categoryId=:categoryId")
    suspend fun getRecipesById(categoryId: Int) : List<Recipe>

    @Query("SELECT * FROM Recipe WHERE id=:recipeId")
    suspend fun getRecipeById(recipeId: Int) : Recipe

    @Query("SELECT * FROM Recipe")
    suspend fun getAllRecipes() : List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipes(listOfRecipes: List<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Query("SELECT * FROM Recipe WHERE favorites=:isFavorite")
    suspend fun getFavoritesRecipes(isFavorite: Boolean) : List<Recipe>

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

}