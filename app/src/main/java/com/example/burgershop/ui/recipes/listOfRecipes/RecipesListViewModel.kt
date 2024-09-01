package com.example.burgershop.ui.recipes.listOfRecipes

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.burgershop.data.STUB
import com.example.burgershop.model.Recipe

class RecipesListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val _listOfRecipesUiState = MutableLiveData(RecipesUiState())
    val listOfRecipesUiState: LiveData<RecipesUiState> = _listOfRecipesUiState

    fun openRecipesByCategoryId(categoryId: Int) {
        val idOfCategories = STUB.getRecipesByCategoryId(categoryId)
        val listOfCategory = STUB.getCategories()
        val nameOfCategory = listOfCategory[categoryId].title
        val urlImage = listOfCategory[categoryId].imgUrl

        val drawable = Drawable.createFromStream(urlImage.let {
            application.assets?.open(it)
        }, null)


        try {
            _listOfRecipesUiState.value = RecipesUiState(
                listOfRecipes = idOfCategories,
                categoryImage = drawable,
                titleOfCategories = nameOfCategory,
                imageUrl = urlImage
            )
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