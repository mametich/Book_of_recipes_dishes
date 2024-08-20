package com.example.burgershop.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.burgershop.R
import com.example.burgershop.SET_ID
import com.example.burgershop.SHARED_PREF_BURGER_SHOP
import com.example.burgershop.data.STUB
import com.example.burgershop.model.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel(private val application: Application) : AndroidViewModel(application) {

    private val _recipeUiSt = MutableLiveData(RecipeUiState())
    val recipeUiSt: LiveData<RecipeUiState> = _recipeUiSt

    //TODO load from network
    fun loadRecipe(recipeId: Int) {
        val newRecipe = STUB.getRecipeById(recipeId)
        /* Иван не могу понять куда поставить проверку
        наличия в хранилище переданного id рецепта
        при инициализации какого свойства?
        */
        getFavorites().contains(newRecipe.id.toString())

        val drawable = Drawable.createFromStream(
            newRecipe.imageUrl.let { application.assets.open(it) },
            null
        )

        try {
            _recipeUiSt.value = RecipeUiState(
                recipe = newRecipe,
                recipeImage = drawable
            )
        } catch (e: Exception) {
            Log.e("MyTag", "Assets is null")
            _recipeUiSt.value = RecipeUiState(
                recipeImage = null
            )
        }
    }

    fun onFavoritesClicked() {
        val idOfRecipe = recipeUiSt.value?.recipe?.id.toString()
        val setOfId = getFavorites()

        if (setOfId.contains(idOfRecipe)) {
            val currentState = recipeUiSt.value?.copy(
                isFavorite = false
            )
            _recipeUiSt.value = currentState
            setOfId.remove(idOfRecipe)
            saveFavorites(setOfId)
        } else {
            val currentState = recipeUiSt.value?.copy(
                isFavorite = true
            )
            setOfId.add(idOfRecipe)
            saveFavorites(setOfId)
            _recipeUiSt.value = currentState
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