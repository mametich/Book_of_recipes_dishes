package com.example.burgershop.ui.category

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.burgershop.MyApplication
import com.example.burgershop.RecipesRepository
import com.example.burgershop.data.STUB
import com.example.burgershop.model.Category
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class CategoriesListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

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
                Toast.makeText(application.baseContext, "Ошибка получения данных", Toast.LENGTH_SHORT).show()
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