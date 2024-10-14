package com.example.burgershop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.burgershop.model.Category
import com.example.burgershop.model.Constants
import com.example.burgershop.model.Recipe

@Database(entities = [Category::class, Recipe::class], version = 3, exportSchema = false)
@TypeConverters(IngredientsConverter::class, MethodConverter::class)

abstract class CategoryDatabase : RoomDatabase() {

    abstract fun categoriesDao() : CategoriesDao
    abstract fun recipesDao() : RecipesDao

    companion object {
        @Volatile
        private var INSTANCE: CategoryDatabase? = null

        fun getDatabase(context: Context) : CategoryDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CategoryDatabase::class.java,
                    Constants.CATEGORY_DATABASE
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}