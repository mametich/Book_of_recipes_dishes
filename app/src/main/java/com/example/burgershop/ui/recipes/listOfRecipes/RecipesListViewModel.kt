package com.example.burgershop.ui.recipes.listOfRecipes

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.burgershop.RecipesRepository
import com.example.burgershop.model.Recipe
import kotlinx.coroutines.launch

class RecipesListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val recipesRepository = RecipesRepository()

    private val _listOfRecipesUiState = MutableLiveData(RecipesUiState())
    val listOfRecipesUiState: LiveData<RecipesUiState> = _listOfRecipesUiState

    fun openRecipesByCategoryId(categoryId: Int) {
        try {
            viewModelScope.launch {
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
            }
            viewModelScope.launch {
                recipesRepository.getCategoryById(categoryId) { category ->
                    if (category != null) {
                        _listOfRecipesUiState.postValue(
                            _listOfRecipesUiState.value?.copy(
                                titleOfCategories = category.title,
                                imageUrl = category.imgUrl,
                                categoryImage = category.title
                            )
                        )
                    }
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