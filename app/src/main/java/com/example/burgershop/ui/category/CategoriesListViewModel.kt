package com.example.burgershop.ui.category

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.burgershop.RecipesRepository
import com.example.burgershop.model.Category

class CategoriesListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private var recipesRepository = RecipesRepository()

    private val _categoryListUiState = MutableLiveData(CategoriesListUiState())
    val categoryListUiState: LiveData<CategoriesListUiState> = _categoryListUiState

    fun loadListOfCategory() {
        recipesRepository.getAllCategories { categories ->
            if (categories.isNotEmpty()) {
                _categoryListUiState.postValue(
                    _categoryListUiState.value?.copy(
                        listOfCategory = categories
                    )
                )
            } else {
                Log.e("MyTag", "No categories found or categories are null")
                _categoryListUiState.postValue(
                    _categoryListUiState.value?.copy(
                        listOfCategory = null
                    )
                )
            }
        }
    }

    data class CategoriesListUiState(
        val listOfCategory: List<Category>? = emptyList()
    )
}