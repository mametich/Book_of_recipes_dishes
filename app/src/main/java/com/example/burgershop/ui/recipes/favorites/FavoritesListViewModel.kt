package com.example.burgershop.ui.recipes.favorites

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.burgershop.SET_ID
import com.example.burgershop.SHARED_PREF_BURGER_SHOP
import com.example.burgershop.data.STUB
import com.example.burgershop.model.Recipe


class FavoritesListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val _favoritesUiState = MutableLiveData(FavoritesUiState())
    val favoritesUiState: LiveData<FavoritesUiState> = _favoritesUiState

    fun loadListOfRecipes() {
        val setOfIds = getFavorites()
        val newListOfRecipes = STUB.getRecipesByIds(setOfIds)

        try {
            _favoritesUiState.value = FavoritesUiState(
                listOfFavoriteRecipes = newListOfRecipes
            )
        } catch (e: Exception) {
            Log.e("MyTag", "Error favorites is null")
            _favoritesUiState.value = null
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPref = application.getSharedPreferences(
            SHARED_PREF_BURGER_SHOP, Context.MODE_PRIVATE
        )
        return HashSet(sharedPref?.getStringSet(SET_ID, HashSet<String>()) ?: mutableSetOf())
    }

    data class FavoritesUiState(
        val listOfFavoriteRecipes: List<Recipe> = emptyList(),

        )
}