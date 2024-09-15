package com.example.burgershop.ui.recipes.listOfRecipes

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.burgershop.MyApplication
import com.example.burgershop.RecipesRepository
import com.example.burgershop.data.STUB
import com.example.burgershop.model.Category
import com.example.burgershop.model.Recipe
import com.example.burgershop.ui.category.CategoriesListViewModel.CategoriesListUiState
import java.util.concurrent.TimeUnit

class RecipesListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val recipesRepository = RecipesRepository()

    private val _listOfRecipesUiState = MutableLiveData(RecipesUiState())
    val listOfRecipesUiState: LiveData<RecipesUiState> = _listOfRecipesUiState

    fun openRecipesByCategoryId(categoryFromList: Category) {
        try {
            recipesRepository.getRecipesById(categoryFromList.id) {
                _listOfRecipesUiState.value = RecipesUiState(
                    listOfRecipes = it,
                    titleOfCategories = categoryFromList.title,
                    imageUrl = categoryFromList.imgUrl,
                    categoryImage = Drawable.createFromStream(categoryFromList.let {
                        application.assets?.open(it.toString())
                    }, null)
                )
            }
        } catch (e: Exception) {
            Log.e("MyTag", "Error listOfRecipes is null")
            _listOfRecipesUiState.value = null
        }
    }

    data class RecipesUiState(
        val listOfRecipes: List<Recipe> = emptyList(),
        val categoryImage: Drawable? = null,
        val titleOfCategories: String = "",
        val imageUrl: String = "",
    )

}