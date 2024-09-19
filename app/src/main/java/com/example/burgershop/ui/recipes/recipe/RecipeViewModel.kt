package com.example.burgershop.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.burgershop.RecipesRepository
import com.example.burgershop.SET_ID
import com.example.burgershop.SHARED_PREF_BURGER_SHOP
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
                        recipeImage = Drawable.createFromStream(
                            recipe.imageUrl.let { application.assets?.open(it) },
                            null
                        )
                    )
                )
            } else {
                _recipeUiSt.postValue(null)
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
            SHARED_PREF_BURGER_SHOP, Context.MODE_PRIVATE
        )
        return HashSet(sharedPr.getStringSet(SET_ID, HashSet<String>()) ?: mutableSetOf())
    }

    private fun saveFavorites(setId: Set<String>) {
        val sharedPref = application.getSharedPreferences(
            SHARED_PREF_BURGER_SHOP, Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            putStringSet(SET_ID, setId)
            apply()
        }
    }

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val portionsCount: Int = 1,
        val isFavorite: Boolean = false,
        val recipeImage: Drawable? = null,
    )
}