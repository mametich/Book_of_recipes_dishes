package com.example.burgershop.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.burgershop.SET_ID
import com.example.burgershop.SHARED_PREF_BURGER_SHOP
import com.example.burgershop.data.STUB
import com.example.burgershop.model.Recipe

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _recipeUiSt = MutableLiveData(RecipeUiState())
    val recipeUiSt: LiveData<RecipeUiState> = _recipeUiSt

    //TODO load from network
    fun loadRecipe(recipeId: Int) {
        val newRecipe = STUB.getRecipeById(recipeId)

        val drawable = Drawable.createFromStream(
            newRecipe.imageUrl.let { application.assets.open(it) },
            null
        )

        try {
            _recipeUiSt.value = RecipeUiState(
                recipe = newRecipe,
                isFavorite = getFavorites().contains(newRecipe.id.toString()),
                recipeImage = drawable
            )
        } catch (e: Exception) {
            Log.e("MyTag", "Error assets is null")
            _recipeUiSt.value = null
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