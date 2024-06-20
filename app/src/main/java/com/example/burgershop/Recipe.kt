package com.example.burgershop


data class Recipe(
    val id: Int,
    val title: String,
    val listOfIngredients: List<Ingredient>,
    val method: List<String>,
    val imageUrl: String,
)
