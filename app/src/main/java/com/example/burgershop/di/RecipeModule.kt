package com.example.burgershop.di

import android.content.Context
import com.example.burgershop.data.CategoriesDao
import com.example.burgershop.data.CategoryDatabase
import com.example.burgershop.data.RecipesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RecipeModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : CategoryDatabase {
        return CategoryDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideCategoryDao(categoryDatabase: CategoryDatabase) : CategoriesDao = categoryDatabase.categoriesDao()

    @Singleton
    @Provides
    fun provideRecipesDao(categoryDatabase: CategoryDatabase) : RecipesDao = categoryDatabase.recipesDao()
}