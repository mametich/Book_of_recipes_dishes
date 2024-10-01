package com.example.burgershop

import android.os.Handler
import android.os.Looper
import com.example.burgershop.data.api.RecipeApiService
import com.example.burgershop.model.Category
import com.example.burgershop.model.Constants
import com.example.burgershop.model.Recipe
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


class RecipesRepository {

    private val executorService = MyApplication().executorService

    private val contentType = Constants.CONTENT_TYPE.toMediaType()
    private val resultHandler = Handler(Looper.getMainLooper())

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


    fun getAllCategories(callback: (List<Category>) -> Unit) {
        executorService.execute {
            try {
                val responseCall: Call<List<Category>> = serviceApi.getCategories()
                val categoryResponse: Response<List<Category>>? = responseCall.execute()
                if (categoryResponse?.isSuccessful == true && categoryResponse.body() != null) {
                    resultHandler.post {
                        callback(categoryResponse.body() ?: emptyList())
                    }
                } else {
                    resultHandler.post {
                        callback(emptyList())
                    }
                }
            } catch (e: Exception) {
                callback(emptyList())
            }
        }
    }

    fun getRecipesById(categoryId: Int, callback: (List<Recipe>) -> Unit) {
        executorService.execute {
            try {
                val recipesCall = serviceApi.getRecipesById(categoryId)
                val recipesResponse = recipesCall.execute()
                if (recipesResponse.isSuccessful && recipesResponse.body() != null) {
                    resultHandler.post {
                        callback(recipesResponse.body() ?: emptyList())
                    }
                } else {
                    resultHandler.post {
                        callback(emptyList())
                    }
                }
            } catch (e: Exception) {
                callback(emptyList())
            }
        }
    }

    fun getRecipeById(id: Int, callback: (Recipe?) -> Unit) {
        executorService.execute {
            try {
                val recipeCall = serviceApi.getRecipeById(id)
                val recipeResponse = recipeCall.execute()
                if (recipeResponse.isSuccessful && recipeResponse.body() != null) {
                    resultHandler.post {
                        callback(recipeResponse.body())
                    }
                } else {
                    resultHandler.post {
                        callback(null)
                    }
                }
            } catch (e: Exception) {
                callback(null)
            }
        }
    }

    fun getRecipesByIds(ids: String, callback: (List<Recipe>) -> Unit) {
        executorService.execute {
            try {
                val recipesCall = serviceApi.getRecipesByIds(ids)
                val recipesResponse = recipesCall.execute()

                if (recipesResponse.isSuccessful && recipesResponse.body()?.isNotEmpty() == true) {
                    resultHandler.post {
                        callback(recipesResponse.body() ?: emptyList())
                    }
                } else {
                    resultHandler.post {
                        callback(emptyList())
                    }
                }
            } catch (e: Exception) {
                resultHandler.post {
                    callback(emptyList())
                }
            }
        }
    }

    fun getCategoryById(categoryId: Int, callback: (Category?) -> Unit) {
        executorService.execute {
            try {
                val recipesCallById = serviceApi.getCategoryById(categoryId)
                val recipesByIdResponse = recipesCallById.execute()

                if (recipesByIdResponse.isSuccessful && recipesByIdResponse.body() != null) {
                    resultHandler.post {
                        callback(recipesByIdResponse.body())
                    }
                } else {
                    resultHandler.post {
                        callback(null)
                    }
                }
            } catch (e: Exception) {
                resultHandler.post {
                    callback(null)
                }
            }
        }
    }
}

