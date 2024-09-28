package com.example.burgershop.ui.recipes.recipe

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.burgershop.RecipesRepository
import com.example.burgershop.model.Constants
import com.example.burgershop.model.Recipe

class RecipeViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val recipesRepository = RecipesRepository()

    private val _recipeUiSt = MutableLiveData(RecipeUiState())
    val recipeUiSt: LiveData<RecipeUiState> = _recipeUiSt

    fun loadRecipe(recipeId: Int) {
        recipesRepository.getRecipeById(recipeId) { recipe ->
            if (recipe != null) {
                _recipeUiSt.postValue(
                    _recipeUiSt.value?.copy(
                        recipe = recipe,
                        isFavorite = getFavorites().contains(recipe.id.toString()),
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
        val idOfRecipe = recipeUiSt.value?.recipe?.id.toString()
        val setOfId = getFavorites()

        if (setOfId.contains(idOfRecipe)) {
            _recipeUiSt.value = recipeUiSt.value?.copy(isFavorite = false)
            setOfId.remove(idOfRecipe)
            saveFavorites(setOfId)
        } else {
            _recipeUiSt.value = recipeUiSt.value?.copy(isFavorite = true)
            setOfId.add(idOfRecipe)
            saveFavorites(setOfId)
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPr = application.getSharedPreferences(
            Constants.SHARED_PREF_BURGER_SHOP, Context.MODE_PRIVATE
        )
        return HashSet(sharedPr.getStringSet(Constants.SET_ID, HashSet<String>()) ?: mutableSetOf())
    }

    private fun saveFavorites(setId: Set<String>) {
        val sharedPref = application.getSharedPreferences(
            Constants.SHARED_PREF_BURGER_SHOP, Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            putStringSet(Constants.SET_ID, setId)
            apply()
        }
    }

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val portionsCount: Int = 1,
        val isFavorite: Boolean = false,
        val recipeImage: String? = null,
    )
}