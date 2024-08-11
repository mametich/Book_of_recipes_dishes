package com.example.burgershop.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.burgershop.model.Category
import com.example.burgershop.model.Ingredient
import com.example.burgershop.model.Recipe

class RecipeViewModel : ViewModel() {

    private val _recipeUiState  = MutableLiveData(RecipeUiState())
    var recipeUiState: LiveData<RecipeUiState> = _recipeUiState

    init {
        recipeUiState.value?.isFavorite = true
        Log.d("MuLog", "${recipeUiState.value?.isFavorite}")
    }

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val portionsCount: Int = 1,
        var isFavorite: Boolean = false,
    )

}