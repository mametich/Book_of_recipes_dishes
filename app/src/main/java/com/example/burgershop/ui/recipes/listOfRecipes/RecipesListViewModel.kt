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

    fun loadListOfRecipes(categoryId: Int, categoryImage: String?, categoryName: String) {
        val idOfCategories = STUB.getRecipesByCategoryId(categoryId)

        val drawable = Drawable.createFromStream(categoryImage?.let {
            application.assets?.open(it)
        },null)

        try {
            _listOfRecipesUiState.value = RecipesUiState(
                listOfRecipes = idOfCategories,
                categoryImage = drawable,
                titleOfCategories = categoryName
            )
        } catch (e: Exception) {
            Log.e("MyTag", "Error listOfRecipes is null")
            _listOfRecipesUiState.value = null
        }
    }

    fun loadFromMemoryId(id: Int) : Int {
        val recipe = STUB.getRecipeById(id)
        return recipe.id
    }


    data class RecipesUiState(
        val listOfRecipes: List<Recipe> = emptyList(),
        val categoryImage: Drawable? = null,
        val titleOfCategories: String = "",
    )

}