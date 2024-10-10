package com.example.burgershop.data

import androidx.room.TypeConverter
import com.example.burgershop.model.Ingredient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class IngredientsConverter {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromIngredients(listOfIngredients: List<Ingredient>) : String {
        return json.encodeToString(listOfIngredients)
    }

    @TypeConverter
    fun toIngredients(ingredients: String) : List<Ingredient> {
        return json.decodeFromString(ingredients)
    }
}