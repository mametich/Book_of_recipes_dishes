package com.example.burgershop

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.burgershop.databinding.ActivityMainBinding
import com.example.burgershop.model.Category
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val thread = Thread {

            val logger = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            val request = Request.Builder()
                .url("https://recipes.androidsprint.ru/api/category")
                .build()

            val responseOkHttp: Response = client.newCall(request).execute()
            val listOfCategory = responseOkHttp.body?.string()

            val json = Json { ignoreUnknownKeys = true }

            val response = listOfCategory?.let { json.decodeFromString<List<Category>>(it) }

            val responseIdList = response?.map { it.id }

            responseIdList?.forEach {
                threadPool.execute {
                    val loggingId = HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }

                    val clientId = OkHttpClient.Builder()
                        .addInterceptor(loggingId)
                        .build()

                    val requestId = Request.Builder()
                        .url("https://recipes.androidsprint.ru/api/category/${it}/recipes")
                        .build()

                    val responseId = clientId.newCall(requestId).execute()
                    responseId.body?.string()
                }
            }
            threadPool.shutdown()
        }
        thread.start()


        binding.buttonCategories.setOnClickListener {
            goOnCategoriesFragment()
        }

        binding.buttonFavourites.setOnClickListener {
            goOnFavouritesFragment()
        }
    }

    private fun goOnCategoriesFragment() {
        findNavController(R.id.nav_host_fragment).navigate(R.id.categoriesListFragment)
    }

    private fun goOnFavouritesFragment() {
        findNavController(R.id.nav_host_fragment).navigate(R.id.favoritesListFragment)
    }
}