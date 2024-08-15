package com.example.burgershop.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.util.Log
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

class RecipeViewModel : AndroidViewModel(application = Application()) {

    private val _recipeUiSt = MutableLiveData(RecipeUiState())
    val recipeUiSt: LiveData<RecipeUiState> = _recipeUiSt

    //TODO load from network
    fun loadRecipe(recipeId: Int) {
        val newRecipe = STUB.getRecipeById(recipeId)
        val setOfId = getFavorites()
        val currentState = if (setOfId.contains(newRecipe.id.toString()))
            RecipeUiState(
                recipe = newRecipe,
                isFavorite = true
            ) else
            RecipeUiState(
                recipe = newRecipe,
            )
        _recipeUiSt.value = currentState
    }

//    init {
//        val updatesState = recipeUiSt.value?.copy(
//            isFavorite = true
//        )
//        _recipeUiSt.value = updatesState
//        Log.d("MyLog", "${recipeUiSt.value?.isFavorite}")
//    }

    fun onFavoritesClicked() {
        val idOfRecipe = recipeUiSt.value?.recipe?.id.toString()
        val setOfId = getFavorites()

        val currentState = if (setOfId.contains(idOfRecipe)) {
           recipeUiSt.value?.copy(
               isFavorite = true
           )
        } else {
            recipeUiSt.value?.copy(
                isFavorite = false
            )
        }
        _recipeUiSt.value = currentState
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPr = getApplication<Application>().getSharedPreferences(
            SHARED_PREF_BURGER_SHOP, Context.MODE_PRIVATE
        )
        return HashSet(sharedPr.getStringSet(SET_ID, HashSet<String>()) ?: mutableSetOf())
    }

    private fun saveFavorites(setId: Set<String>) {
        val sharedPref = getApplication<Application>()
            .getSharedPreferences(SHARED_PREF_BURGER_SHOP, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putStringSet(SET_ID, setId)
            apply()
        }
    }


    data class RecipeUiState(
        val recipe: Recipe? = null,
        val portionsCount: Int = 1,
        val isFavorite: Boolean = false,
    )

}