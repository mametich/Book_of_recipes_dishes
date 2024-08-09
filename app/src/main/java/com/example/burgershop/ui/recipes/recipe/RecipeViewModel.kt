package com.example.burgershop.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.example.burgershop.model.Category
import com.example.burgershop.model.Ingredient
import com.example.burgershop.model.Recipe

data class RecipeUiState(
    val listOfIngredients: List<Ingredient> = emptyList(),
    val listOfRecipes: List<Recipe> = emptyList(),
    val listOfCategories: List<Category> = emptyList(),
    val recipe: Recipe? = null,
    val listOfRecipesById: List<Recipe> = emptyList()
)

class RecipeViewModel : ViewModel() {


}