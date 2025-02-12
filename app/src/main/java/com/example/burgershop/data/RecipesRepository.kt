package com.example.burgershop.data

import com.example.burgershop.data.api.RecipeApiService
import com.example.burgershop.model.Category
import com.example.burgershop.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipesRepository @Inject constructor (
    private val categoriesDao: CategoriesDao,
    private val recipesDao: RecipesDao,
    private val recipeApiService: RecipeApiService,
) {

    suspend fun getCategories(): List<Category> {
        var categories = getAllCategoriesFromCache()
        if (categories.isEmpty()) {
            categories = getAllCategoriesFromApi()
            categoriesDao.addCategory(categories)
        }
        return categories
    }

    private suspend fun getAllCategoriesFromCache(): List<Category> {
        return categoriesDao.getAllCategories()
    }

    private suspend fun getAllCategoriesFromApi(): List<Category> {
        return withContext(Dispatchers.IO) {
            try {
                val responseCall: Call<List<Category>> = recipeApiService.getCategories()
                val categoryResponse: Response<List<Category>>? = responseCall.execute()
                if (categoryResponse?.isSuccessful == true && categoryResponse.body() != null) {
                    categoryResponse.body() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getRecipes(categoryId: Int): List<Recipe> {
        var recipes = getRecipesFromCache(categoryId)
        if (recipes.isEmpty()) {
            recipes = getRecipesByIdFromApi(categoryId)
            if (recipes.isNotEmpty()) {
                recipes = recipes.map { it.copy(categoryId = categoryId) }
                recipesDao.addRecipes(recipes)
            }
        }
        return recipes
    }

    private suspend fun getRecipesFromCache(categoryId: Int): List<Recipe> {
        return recipesDao.getRecipesById(categoryId)
    }

    private suspend fun getRecipesByIdFromApi(categoryId: Int): List<Recipe> {
        return withContext(Dispatchers.IO) {
            try {
                val recipesCall = recipeApiService.getRecipesById(categoryId)
                val recipesResponse = recipesCall.execute()
                if (recipesResponse.isSuccessful && recipesResponse.body() != null) {
                    recipesResponse.body() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getRecipesByFavorites(isFavorites: Boolean): List<Recipe> {
        return recipesDao.getFavoritesRecipes(isFavorites)
    }

    suspend fun updateRecipe(recipe: Recipe) {
        recipesDao.updateRecipe(recipe)
    }

    suspend fun getRecipeByIdFromCache(id: Int): Recipe {
        return recipesDao.getRecipeById(id)
    }
}



