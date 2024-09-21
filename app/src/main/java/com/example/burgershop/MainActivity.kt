package com.example.burgershop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.burgershop.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


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