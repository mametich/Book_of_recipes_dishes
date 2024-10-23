package com.example.burgershop.ui.recipes.favorites


import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.burgershop.RecipesRepository
import com.example.burgershop.model.Recipe
import kotlinx.coroutines.launch


class FavoritesListViewModel(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    private val _favoritesUiState = MutableLiveData(FavoritesUiState())
    val favoritesUiState: LiveData<FavoritesUiState> = _favoritesUiState

    fun loadListOfRecipes() {
        viewModelScope.launch {
            try {
                val recipes = recipesRepository.getRecipesByFavorites(true)
                if (recipes.isNotEmpty()) {
                    _favoritesUiState.postValue(
                        _favoritesUiState.value?.copy(
                            listOfFavoriteRecipes = recipes
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e("MyTag", "Error favorites is null")
                _favoritesUiState.postValue(
                    _favoritesUiState.value?.copy(
                        listOfFavoriteRecipes = null
                    )
                )
            }
        }
    }

    data class FavoritesUiState(
        val listOfFavoriteRecipes: List<Recipe>? = emptyList(),
    )
}