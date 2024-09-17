package com.example.burgershop.ui.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.burgershop.MyApplication
import com.example.burgershop.RecipesRepository
import com.example.burgershop.data.STUB
import com.example.burgershop.model.Category
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class CategoriesListViewModel() : ViewModel() {

    private var recipesRepository = RecipesRepository()

    private val _categoryListUiState = MutableLiveData(CategoriesListUiState())
    val categoryListUiState: LiveData<CategoriesListUiState> = _categoryListUiState

    fun loadListOfCategory() {
        recipesRepository.getAllCategories { categories ->
            if (categories.isNotEmpty()) {
                _categoryListUiState.value = CategoriesListUiState(
                    listOfCategory = categories
                )
            } else {
                Log.e("MyTag", "No categories found or categories are null")
                _categoryListUiState.value = CategoriesListUiState(
                    listOfCategory = emptyList()
                )
            }
        }
    }

    data class CategoriesListUiState(
        val listOfCategory: List<Category> = emptyList()
    )
}