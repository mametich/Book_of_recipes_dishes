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
            recipesRepository.getRecipesById(categoryFromList.id) { recipes ->
                _listOfRecipesUiState.value = RecipesUiState(
                    listOfRecipes = recipes,
                )
            }
            recipesRepository.getCategories { categories ->
                _listOfRecipesUiState.value = RecipesUiState(
                    titleOfCategories = categories[categoryFromList.id].title,
                    imageUrl = categories[categoryFromList.id].imgUrl,
                    categoryImage = Drawable.createFromStream(categoryFromList.imgUrl.let { imgUrl ->
                        application.assets?.open(imgUrl)
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