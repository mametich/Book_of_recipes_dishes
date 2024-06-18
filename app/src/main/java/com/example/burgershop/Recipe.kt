package com.example.burgershop


data class Recipe(
    val id: Int,
    val title: String,
    val ingredients: Ingredients,
    val method: List<String>,
    val imageUrl: String,
)
