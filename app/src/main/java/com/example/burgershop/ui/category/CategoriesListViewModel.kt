package com.example.burgershop.ui.category

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.burgershop.data.STUB
import com.example.burgershop.model.Category

class CategoriesListViewModel : ViewModel() {

    private val _categoryListUiState = MutableLiveData(CategoriesListUiState())
    val categoryListUiState: LiveData<CategoriesListUiState> = _categoryListUiState

    //TODO load from network
    fun loadListOfCategory() {
        val listOfCategory = STUB.getCategories()
        val categoryFromList =

        try {
            _categoryListUiState.value = CategoriesListUiState(
                listOfCategory = listOfCategory
            )
        } catch (e: Exception) {
            Log.e("MyTag", "Error categories is null")
            _categoryListUiState.value = null
        }
    }

    data class CategoriesListUiState(
        val listOfCategory: List<Category> = emptyList(),

    )
}