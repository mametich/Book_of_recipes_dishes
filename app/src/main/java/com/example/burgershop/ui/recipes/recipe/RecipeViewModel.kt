package com.example.burgershop.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.example.burgershop.model.Category
import com.example.burgershop.model.Ingredient
import com.example.burgershop.model.Recipe

class RecipeViewModel : ViewModel() {

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val portionsCount: Int = 1,
        val isFavorite: Boolean = false,
    )
}