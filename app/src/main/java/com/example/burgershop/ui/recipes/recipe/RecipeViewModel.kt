package com.example.burgershop.ui.recipes.recipe

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.burgershop.model.Recipe

class RecipeViewModel : ViewModel() {

    private val _recipeUiSt  = MutableLiveData(RecipeUiState())
    val recipeUiSt: LiveData<RecipeUiState> = _recipeUiSt

    init {
        _recipeUiSt.value = RecipeUiState(
            isFavorite = true
        )
        Log.d("MyLog", "${recipeUiSt.value?.isFavorite}")
    }

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val portionsCount: Int = 1,
        var isFavorite: Boolean = false,
    )

}