package com.example.burgershop.ui.category

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.burgershop.RecipesRepository
import com.example.burgershop.model.Category
import kotlinx.coroutines.launch

class CategoriesListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private var recipesRepository = RecipesRepository(application.baseContext)

    private val _categoryListUiState = MutableLiveData(CategoriesListUiState())
    val categoryListUiState: LiveData<CategoriesListUiState> = _categoryListUiState

    fun loadListOfCategory() {
        viewModelScope.launch {
            val categories = recipesRepository.getCategoriesFromCache()

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
}

data class CategoriesListUiState(
    val listOfCategory: List<Category>? = emptyList()
)
