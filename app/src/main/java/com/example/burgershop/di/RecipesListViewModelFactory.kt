package com.example.burgershop.di

import com.example.burgershop.RecipesRepository
import com.example.burgershop.ui.recipes.listOfRecipes.RecipesListViewModel

class RecipesListViewModelFactory(
    private val recipesRepository: RecipesRepository
) : Factory<RecipesListViewModel> {
    override fun create(): RecipesListViewModel {
        return RecipesListViewModel(recipesRepository)
    }
}