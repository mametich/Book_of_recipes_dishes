package com.example.burgershop

import com.example.burgershop.api.RecipeApiService
import com.example.burgershop.model.Category
import com.example.burgershop.model.Recipe
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


private const val BASE_URL = "https://recipes.androidsprint.ru/api/"

class RecipesRepository() {

    private val contentType = "application/json".toMediaType()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

    private val serviceApi: RecipeApiService =
        retrofit.create(RecipeApiService::class.java)


    fun getCategories(): List<Category> {
        val result: MutableList<Category> = mutableListOf()
        val thread = Thread {
            val responseCall: Call<List<Category>> = serviceApi.getCategories()
            val categoryResponse: Response<List<Category>> = responseCall.execute()
            val categories: List<Category>? = categoryResponse.body()
            if (categories != null) {
                result.addAll(categories)
            }
        }
        thread.start()
        thread.join()
        return result
    }

    fun getCategoryById(id: Int): Category? {
        var category: Category? = null
        val thread = Thread {
            val categoryCall = serviceApi.getCategoryById(id)
            val categoryResponse = categoryCall?.execute()
            val categoryFromNet = categoryResponse?.body()
            if (categoryFromNet != null) {
                category = categoryFromNet
            }
        }
        thread.start()
        thread.join()
        return category
    }

    fun getRecipesById(id: Int): List<Recipe> {
        val result: MutableList<Recipe> = mutableListOf()
        val thread = Thread {
            val recipesCall = serviceApi.getRecipesById(id)
            val recipesResponse = recipesCall.execute()
            val recipes: List<Recipe>? = recipesResponse.body()
            if (recipes != null) {
                result.addAll(recipes)
            }
        }
        thread.start()
        thread.join()
        return result
    }

    fun getRecipes(ids: MutableSet<String>): List<Recipe> {
        val result: MutableList<Recipe> = mutableListOf()
        val thread = Thread {
            val recipesCall = serviceApi.getRecipes(ids)
            val recipesResponse = recipesCall.execute()
            val recipesByIds = recipesResponse.body()
            if (recipesByIds != null) {
                result.addAll(result.filter { ids.contains(it.id.toString()) })
            }
        }
        thread.start()
        thread.join()
        return result
    }

    fun getRecipeById(id: Int): Recipe? {
        var recipeBiId: Recipe? = null
        val thread = Thread {
            val recipeCall = serviceApi.getRecipeById(id)
            val recipeResponse = recipeCall.execute()
            val recipe = recipeResponse.body()
            if (recipe != null) {
                recipeBiId = recipe
            }
        }
        thread.start()
        thread.join()
        return recipeBiId
    }

}

