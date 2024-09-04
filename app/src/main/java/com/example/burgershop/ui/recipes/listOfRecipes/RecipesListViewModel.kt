package com.example.burgershop.ui.recipes.listOfRecipes

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.burgershop.data.STUB
import com.example.burgershop.model.Category
import com.example.burgershop.model.Recipe

class RecipesListViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val _listOfRecipesUiState = MutableLiveData(RecipesUiState())
    val listOfRecipesUiState: LiveData<RecipesUiState> = _listOfRecipesUiState

    fun openRecipesByCategoryId(categoryFromList: Category) {

        val idOfCategories = try {
            STUB.getRecipesByCategoryId(categoryFromList.id)
        } catch (e: Exception) {
            throw IllegalArgumentException("category is null")
        }

        val listOfCategory = STUB.getCategories()
        val nameOfCategory = listOfCategory[categoryFromList.id].title
        val urlImage = listOfCategory[categoryFromList.id].imgUrl

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