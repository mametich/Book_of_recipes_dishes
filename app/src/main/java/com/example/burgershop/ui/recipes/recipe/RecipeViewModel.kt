package com.example.burgershop.ui.recipes.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.burgershop.data.RecipesRepository
import com.example.burgershop.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    private val _recipeUiSt = MutableLiveData(RecipeUiState())
    val recipeUiSt: LiveData<RecipeUiState> = _recipeUiSt

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            val recipe = recipesRepository.getRecipeByIdFromCache(recipeId)
            if (recipe != null) {
                _recipeUiSt.postValue(
                    _recipeUiSt.value?.copy(
                        recipe = recipe,
                        isFavorite = getFavorites().contains(recipe),
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
            val recipe = _recipeUiSt.value?.recipe
            if (recipe != null) {
                val updatedRecipe = recipe.copy(isFavorites = !recipe.isFavorites)
                _recipeUiSt.value = _recipeUiSt.value?.copy(
                    recipe = updatedRecipe,
                    isFavorite = !recipe.isFavorites
                )
                recipesRepository.updateRecipe(updatedRecipe)
            }
        }
    }

    private suspend fun getFavorites() : List<Recipe> {
        return recipesRepository.getRecipesByFavorites(true)
    }

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val portionsCount: Int = 1,
        val isFavorite: Boolean = false,
        val recipeImage: String? = null,
    )
}
