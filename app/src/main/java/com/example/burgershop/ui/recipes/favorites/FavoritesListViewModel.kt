package com.example.burgershop.ui.recipes.favorites

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.burgershop.RecipesRepository
import com.example.burgershop.SET_ID
import com.example.burgershop.SHARED_PREF_BURGER_SHOP
import com.example.burgershop.model.Recipe


class FavoritesListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val recipesRepository = RecipesRepository()

    private val _favoritesUiState = MutableLiveData(FavoritesUiState())
    val favoritesUiState: LiveData<FavoritesUiState> = _favoritesUiState

    fun loadListOfRecipes() {
        val setOfIds = getFavorites().joinToString(",")
        try {
            recipesRepository.getRecipesByIds(setOfIds) { recipes ->
                if (recipes.isNotEmpty()) {
                    _favoritesUiState.postValue(
                        _favoritesUiState.value?.copy(
                            listOfFavoriteRecipes = recipes
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("MyTag", "Error favorites is null")
            _favoritesUiState.postValue(null)
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPref = application.getSharedPreferences(
            SHARED_PREF_BURGER_SHOP, Context.MODE_PRIVATE
        )
        return HashSet(sharedPref?.getStringSet(SET_ID, HashSet<String>()) ?: mutableSetOf())
    }

    data class FavoritesUiState(
        val listOfFavoriteRecipes: List<Recipe>? = emptyList(),
    )
}