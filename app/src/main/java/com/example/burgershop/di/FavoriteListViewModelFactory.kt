package com.example.burgershop.di

import com.example.burgershop.RecipesRepository
import com.example.burgershop.ui.recipes.favorites.FavoritesListViewModel

class FavoriteListViewModelFactory(
    private val recipesRepository: RecipesRepository
) : Factory<FavoritesListViewModel> {
    override fun create(): FavoritesListViewModel {
        return FavoritesListViewModel(recipesRepository)
    }
}