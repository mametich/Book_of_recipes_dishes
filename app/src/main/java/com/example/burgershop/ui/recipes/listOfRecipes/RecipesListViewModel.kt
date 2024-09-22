package com.example.burgershop.ui.recipes.listOfRecipes

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.burgershop.RecipesRepository
import com.example.burgershop.model.Recipe

class RecipesListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val recipesRepository = RecipesRepository()

    private val _listOfRecipesUiState = MutableLiveData(RecipesUiState())
    val listOfRecipesUiState: LiveData<RecipesUiState> = _listOfRecipesUiState

    fun openRecipesByCategoryId(categoryId: Int) {
        try {
            recipesRepository.getRecipesById(categoryId) { recipes ->
                if (recipes.isNotEmpty()) {
                    _listOfRecipesUiState.postValue(
                        _listOfRecipesUiState.value?.copy(
                            listOfRecipes = recipes
                        )
                    )
                } else {
                    _listOfRecipesUiState.postValue(
                        _listOfRecipesUiState.value?.copy(
                            listOfRecipes = null
                        )
                    )
                }
            }
            recipesRepository.getAllCategories { categories ->
                if (categories.isNotEmpty()) {
                    _listOfRecipesUiState.postValue(
                        _listOfRecipesUiState.value?.copy(
                            titleOfCategories = categories[categoryId].title,
                            imageUrl = categories[categoryId].imgUrl,
                            categoryImage = categories[categoryId].title
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("MyTag", "Error listOfRecipes is null")
            _listOfRecipesUiState.postValue(
                _listOfRecipesUiState.value?.copy(
                    titleOfCategories = null,
                    imageUrl = null,
                )
            )
        }
    }

    data class RecipesUiState(
        val listOfRecipes: List<Recipe>? = emptyList(),
        val categoryImage: String? = null,
        val titleOfCategories: String? = "",
        val imageUrl: String? = "",
    )
}