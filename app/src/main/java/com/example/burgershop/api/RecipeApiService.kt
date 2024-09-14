package com.example.burgershop.api

import com.example.burgershop.model.Category
import com.example.burgershop.model.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApiService {

    @GET("category")
    fun getCategories() : Call<List<Category>>

    @GET("category/{id}")
    fun getCategoryById(@Path("id") id: Int) : Call<Category>?

    @GET("category/{id}/recipes")
    fun getRecipesById(@Path("id") id: Int) : Call<List<Recipe>>

    @GET("recipes")
    fun getRecipes(ids: MutableSet<String>) : Call<List<Recipe>>

    @GET("recipe/{id}")
    fun getRecipeById(@Path("id") id: Int) : Call<Recipe>

}