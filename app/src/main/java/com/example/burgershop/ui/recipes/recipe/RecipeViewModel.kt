package com.example.burgershop.ui.recipes.recipe

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.burgershop.model.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val _recipeUiSt  = MutableLiveData(RecipeUiState())
    val recipeUiSt: LiveData<RecipeUiState> = _recipeUiSt

    init {
       val updatesState = recipeUiSt.value?.copy(
           isFavorite = true
       )
        _recipeUiSt.value = updatesState
        Log.d("MyLog", "${recipeUiSt.value?.isFavorite}")
    }

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val portionsCount: Int = 1,
        val isFavorite: Boolean = false,
    )

}