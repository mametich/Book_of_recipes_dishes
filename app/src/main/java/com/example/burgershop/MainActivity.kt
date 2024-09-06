package com.example.burgershop

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.burgershop.databinding.ActivityMainBinding
import com.example.burgershop.model.Category
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")


        val thread = Thread {
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            val jsonString = connection.inputStream.bufferedReader().readText()
            Log.d("!!!", jsonString)

            val json = Json { ignoreUnknownKeys = true }

            val response = json.decodeFromString<List<Category>>(jsonString)
            Log.d("!!!", "$response")

        }
        Log.d("!!!", "Выполняю запрос на потоке: ${thread.name}")
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