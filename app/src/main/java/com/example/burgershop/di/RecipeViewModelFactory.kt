package com.example.burgershop.di

import com.example.burgershop.RecipesRepository
import com.example.burgershop.ui.recipes.recipe.RecipeViewModel

class RecipeViewModelFactory(
    private val recipesRepository: RecipesRepository
) : Factory<RecipeViewModel>{
    override fun create(): RecipeViewModel {
        return RecipeViewModel(recipesRepository)
    }
}