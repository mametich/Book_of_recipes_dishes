package com.example.burgershop.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.burgershop.MyApplication
import com.example.burgershop.RecipesRepository
import com.example.burgershop.SET_ID
import com.example.burgershop.SHARED_PREF_BURGER_SHOP
import com.example.burgershop.data.STUB
import com.example.burgershop.model.Recipe
import java.util.concurrent.TimeUnit

class RecipeViewModel(
    private val application: Application
) : AndroidViewModel(application) {

    private val recipesRepository = RecipesRepository()
    private val myApplication = MyApplication()

    private val _recipeUiSt = MutableLiveData(RecipeUiState())
    val recipeUiSt: LiveData<RecipeUiState> = _recipeUiSt

    fun loadRecipe(recipeId: Int) {
        var recipeById: Recipe? = null
        var drawableFromThread: Drawable? = null

        myApplication.executorService.execute {
            val newRecipe = recipesRepository.getRecipeById(recipeId)
            recipeById = newRecipe
            val drawable = Drawable.createFromStream(
                newRecipe?.imageUrl?.let { application.assets?.open(it) },
                null
            )
            drawableFromThread = drawable
        }

        myApplication.executorService.shutdown()
        myApplication.executorService.awaitTermination(10, TimeUnit.SECONDS)

        try {
            _recipeUiSt.value = RecipeUiState(
                recipe = recipeById,
                isFavorite = getFavorites().contains(recipeById?.id.toString()),
                recipeImage = drawableFromThread
            )
        } catch (e: Exception) {
            Log.e("MyTag", "Error assets is null")
            _recipeUiSt.value = null
        }
    }

    fun updatedCountOfPortion(quantityOfPortion: Int) {
        _recipeUiSt.value = recipeUiSt.value?.copy(portionsCount = quantityOfPortion)
    }

    fun onFavoritesClicked() {
        val idOfRecipe = recipeUiSt.value?.recipe?.id.toString()
        val setOfId = getFavorites()

        if (setOfId.contains(idOfRecipe)) {
            _recipeUiSt.value = recipeUiSt.value?.copy(isFavorite = false)
            setOfId.remove(idOfRecipe)
            saveFavorites(setOfId)
        } else {
            _recipeUiSt.value = recipeUiSt.value?.copy(isFavorite = true)
            setOfId.add(idOfRecipe)
            saveFavorites(setOfId)
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPr = application.getSharedPreferences(
            SHARED_PREF_BURGER_SHOP, Context.MODE_PRIVATE
        )
        return HashSet(sharedPr.getStringSet(SET_ID, HashSet<String>()) ?: mutableSetOf())
    }

    private fun saveFavorites(setId: Set<String>) {
        val sharedPref = application.getSharedPreferences(
            SHARED_PREF_BURGER_SHOP, Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            putStringSet(SET_ID, setId)
            apply()
        }
    }

    data class RecipeUiState(
        val recipe: Recipe? = null,
        val portionsCount: Int = 1,
        val isFavorite: Boolean = false,
        val recipeImage: Drawable? = null,
    )
}