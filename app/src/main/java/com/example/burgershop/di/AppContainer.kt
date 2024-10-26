package com.example.burgershop.di

import android.content.Context
import com.example.burgershop.RecipesRepository
import com.example.burgershop.data.CategoriesDao
import com.example.burgershop.data.CategoryDatabase
import com.example.burgershop.data.RecipesDao
import com.example.burgershop.data.api.RecipeApiService
import com.example.burgershop.model.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class AppContainer(
    context: Context
) {

    private val categoryDao: CategoriesDao =
        CategoryDatabase.getDatabase(context.applicationContext).categoriesDao()
    private val recipesDao: RecipesDao =
        CategoryDatabase.getDatabase(context.applicationContext).recipesDao()

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

    private val repository = RecipesRepository(
        recipesDao = recipesDao,
        categoriesDao = categoryDao,
        recipeApiService = serviceApi,
    )

    val favoriteListViewModelFactory = FavoriteListViewModelFactory(repository)
    val categoryListViewModelFactory = CategoryListViewModelFactory(repository)
    val recipesListViewModelFactory = RecipesListViewModelFactory(repository)
    val recipeListViewModelFactory = RecipeViewModelFactory(repository)
}