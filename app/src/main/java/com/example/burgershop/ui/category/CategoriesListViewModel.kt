package com.example.burgershop.ui.category

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.burgershop.data.STUB
import com.example.burgershop.model.Category

class CategoriesListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val _categoryListUiState = MutableLiveData(CategoriesListUiState())
    val categoryListUiState: LiveData<CategoriesListUiState> = _categoryListUiState

    fun loadListOfCategory() {
        val listOfCategory = STUB.getCategories()

        try {
            _categoryListUiState.value = CategoriesListUiState(
                listOfCategory = listOfCategory
            )
        } catch (e:Exception) {
            Log.e("MyTag", "Error categories is null")
            _categoryListUiState.value = null
        }
    }

    fun loadFromMemoryName(categoryId: Int) : String {
        return STUB.getCategories()[categoryId].title
    }

    fun loadFromMemoryImageUrl(categoryId: Int) : String {
        return STUB.getCategories()[categoryId].imgUrl
    }

    data class CategoriesListUiState(
        val listOfCategory: List<Category> = emptyList(),
    )
}