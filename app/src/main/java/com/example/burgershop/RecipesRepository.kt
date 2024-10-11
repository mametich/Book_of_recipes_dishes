package com.example.burgershop

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.burgershop.data.CategoriesDao
import com.example.burgershop.data.CategoryDatabase
import com.example.burgershop.data.RecipesDao
import com.example.burgershop.data.api.RecipeApiService
import com.example.burgershop.model.Category
import com.example.burgershop.model.Constants
import com.example.burgershop.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


class RecipesRepository(
    context: Context
) {
    private val contentType = Constants.CONTENT_TYPE.toMediaType()

    private val logger = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .client(client)
        .build()

    private val serviceApi: RecipeApiService =
        retrofit.create(RecipeApiService::class.java)

    private val categoryDao: CategoriesDao = CategoryDatabase.getDatabase(context.applicationContext).categoriesDao()
    private val recipesDao: RecipesDao = CategoryDatabase.getDatabase(context.applicationContext).recipesDao()

    suspend fun getCategoriesFromCache() : List<Category> {
        categoryDao.deleteAll()
        val categories = getAllCategories()
        categoryDao.addCategory(categories)
        return categoryDao.getAllCategories()
    }

    suspend fun getRecipesFromCache(id: Int) : List<Recipe> {
        recipesDao.deleteAllRecipes()
        val recipes = getRecipesById(id)
        recipesDao.addRecipes(recipes)
        return recipesDao.getAllRecipes()
    }

    private suspend fun getAllCategories(): List<Category> {
        return withContext(Dispatchers.IO) {
            try {
                val responseCall: Call<List<Category>> = serviceApi.getCategories()
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

    suspend fun getRecipesById(categoryId: Int): List<Recipe> {
        return withContext(Dispatchers.IO) {
            try {
                val recipesCall = serviceApi.getRecipesById(categoryId)
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

    suspend fun getRecipeById(id: Int): Recipe? {
        return withContext(Dispatchers.IO) {
            try {
                val recipeCall = serviceApi.getRecipeById(id)
                val recipeResponse = recipeCall.execute()
                if (recipeResponse.isSuccessful && recipeResponse.body() != null) {
                    recipeResponse.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getRecipesByIds(ids: String): List<Recipe> {
        return withContext(Dispatchers.IO) {
            try {
                val recipesCall = serviceApi.getRecipesByIds(ids)
                val recipesResponse = recipesCall.execute()
                if (recipesResponse.isSuccessful && recipesResponse.body()?.isNotEmpty() == true) {
                    recipesResponse.body() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun getCategoryById(categoryId: Int): Category? {
        return withContext(Dispatchers.IO) {
            try {
                val recipesCallById = serviceApi.getCategoryById(categoryId)
                val recipesByIdResponse = recipesCallById.execute()
                if (recipesByIdResponse.isSuccessful && recipesByIdResponse.body() != null) {
                    recipesByIdResponse.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }
}



