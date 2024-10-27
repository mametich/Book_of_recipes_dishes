package com.example.burgershop.di

import android.content.Context
import com.example.burgershop.RecipesRepository
import com.example.burgershop.data.CategoriesDao
import com.example.burgershop.data.CategoryDatabase
import com.example.burgershop.data.RecipesDao
import com.example.burgershop.data.api.RecipeApiService
import com.example.burgershop.model.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class RecipeModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : CategoryDatabase {
        return CategoryDatabase.getDatabase(context)
    }

    @Provides
    fun provideCategoryDao(categoryDatabase: CategoryDatabase) : CategoriesDao = categoryDatabase.categoriesDao()

    @Provides
    fun provideRecipesDao(categoryDatabase: CategoryDatabase) : RecipesDao = categoryDatabase.recipesDao()
}