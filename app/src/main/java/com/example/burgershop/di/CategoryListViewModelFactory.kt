package com.example.burgershop.di

import com.example.burgershop.RecipesRepository
import com.example.burgershop.ui.category.CategoriesListViewModel

class CategoryListViewModelFactory(
    private val repository: RecipesRepository
) : Factory<CategoriesListViewModel> {

    override fun create(): CategoriesListViewModel {
        return CategoriesListViewModel(repository)
    }

}