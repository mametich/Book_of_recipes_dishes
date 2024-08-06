package com.example.burgershop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
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

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<CategoriesListFragment>(R.id.mainContainer)
            }
        }

        binding.buttonCategories.setOnClickListener {
            goOnCategoriesFragment()
        }

        binding.buttonFavourites.setOnClickListener {
            goOnFavouritesFragment()
        }
    }

    private fun goOnCategoriesFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<CategoriesListFragment>(R.id.mainContainer)
        }
    }

    private fun goOnFavouritesFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<FavoritesListFragment>(R.id.mainContainer)
        }
    }
}