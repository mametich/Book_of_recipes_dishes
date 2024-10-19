package com.example.burgershop.ui.recipes.recipe

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.burgershop.RecipesRepository
import com.example.burgershop.model.Constants
import com.example.burgershop.model.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val recipesRepository = RecipesRepository(application.baseContext)

    private val _recipeUiSt = MutableLiveData(RecipeUiState())
    val recipeUiSt: LiveData<RecipeUiState> = _recipeUiSt

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipesRepository.getRecipeById(recipeId)
            if (recipe != null) {
                _recipeUiSt.postValue(
                    _recipeUiSt.value?.copy(
                        recipe = recipe,
                        isFavorite = getFavorites().any { it.id == recipeUiSt.value?.recipe?.id },
                        recipeImage = recipe.imageUrl
                    )
                )
            } else {
                _recipeUiSt.postValue(
                    _recipeUiSt.value?.copy(
                        recipe = null,
                        isFavorite = false,
                        recipeImage = null
                    )
                )
            }
        }
    }

    fun updatedCountOfPortion(quantityOfPortion: Int) {
        _recipeUiSt.value = recipeUiSt.value?.copy(portionsCount = quantityOfPortion)
    }

    fun onFavoritesClicked() {
        viewModelScope.launch {
            val idOfRecipe = recipeUiSt.value?.recipe?.id
            val listOfRecipe = getFavorites()

            if (listOfRecipe.any { it.id == idOfRecipe }) {
                _recipeUiSt.value = recipeUiSt.value?.copy(isFavorite = false)
                val updatedList = listOfRecipe.toMutableList()
                updatedList.removeIf { it.id == idOfRecipe }
                saveFavorites(updatedList)
            } else {
                _recipeUiSt.value = recipeUiSt.value?.copy(isFavorite = true)
                val recipeToAdd = recipeUiSt.value?.recipe
                if (recipeToAdd != null) {
                    val updatedList = listOfRecipe.toMutableList()
                    updatedList.add(recipeToAdd)
                    saveFavorites(updatedList)
                }
            }
        }
    }

    private suspend fun getFavorites(): List<Recipe> {
        return recipesRepository.getFavoritesRecipesFromCache()
    }

    private suspend fun saveFavorites(favorites: List<Recipe>) {
        recipesRepository.addRecipes(favorites)
    }

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val portionsCount: Int = 1,
        val isFavorite: Boolean = false,
        val recipeImage: String? = null,
    )
}