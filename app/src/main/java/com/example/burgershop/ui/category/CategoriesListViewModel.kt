package com.example.burgershop.ui.category

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.burgershop.RecipesRepository
import com.example.burgershop.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesListViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository
) : ViewModel() {

    private val _categoryListUiState = MutableLiveData(CategoriesListUiState())
    val categoryListUiState: LiveData<CategoriesListUiState> = _categoryListUiState

    fun loadListOfCategory() {
        viewModelScope.launch {
            val categories = recipesRepository.getCategories()

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
