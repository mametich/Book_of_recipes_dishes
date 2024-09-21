package com.example.burgershop.data.api

import com.example.burgershop.model.Category
import com.example.burgershop.model.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApiService {

    @GET("category")
    fun getCategories() : Call<List<Category>>

    @GET("category/{id}")
    fun getCategoryById(@Path("id") id: Int) : Call<Category>?

    @GET("category/{id}/recipes")
    fun getRecipesById(@Path("id") id: Int) : Call<List<Recipe>>

    @GET("recipes")
    fun getRecipesByIds(@Query("ids") ids: String) : Call<List<Recipe>>

    @GET("recipe/{id}")
    fun getRecipeById(@Path("id") id: Int) : Call<Recipe>

}