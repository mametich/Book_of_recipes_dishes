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


class RecipesRepository() {

    private val contentType = CONTENT_TYPE.toMediaType()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()

    private val serviceApi: RecipeApiService =
        retrofit.create(RecipeApiService::class.java)


    //Иван вообще не могу понять, почему этот код не работает приложение крашиться
    fun getCategories(callback: (List<Category>) -> Unit) {
        val thread = Thread {
            try {
                val responseCall: Call<List<Category>> = serviceApi.getCategories()
                val categoryResponse: Response<List<Category>>? = responseCall.execute()
                val categories: List<Category>? = categoryResponse?.body()
                if (categories != null) {
                    callback(categories)
                } else {
                    callback(emptyList())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                callback(emptyList())
            }
        }
        thread.start()
    }

    fun getRecipesById(categoryId: Int, callback: (List<Recipe>) -> Unit) {
        val result: MutableList<Recipe> = mutableListOf()
        val thread = Thread {
            val recipesCall = serviceApi.getRecipesById(categoryId)
            val recipesResponse = recipesCall.execute()
            val recipes: List<Recipe>? = recipesResponse.body()
            if (recipes != null) {
                result.addAll(recipes)
            }
            callback(result)
        }
        thread.start()
    }

    fun getRecipeById(id: Int): Recipe? {
        val recipeCall = serviceApi.getRecipeById(id)
        val recipeResponse = recipeCall.execute()
        val recipe = recipeResponse.body()
        return recipe
    }

    fun getRecipesByIds(ids: MutableSet<String>): List<Recipe> {
        val recipesCall = serviceApi.getRecipes(ids)
        val recipesResponse = recipesCall.execute()
        val recipesByIds = recipesResponse.body()
        recipesByIds?.filter { ids.contains(it.id.toString()) }
        return recipesByIds ?: emptyList()
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

    companion object {
        private const val BASE_URL = "https://recipes.androidsprint.ru/api/"
        private const val CONTENT_TYPE = "application/json"
    }
}

