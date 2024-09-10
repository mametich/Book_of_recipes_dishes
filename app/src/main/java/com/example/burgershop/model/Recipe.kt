package com.example.burgershop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Recipe(
    @SerialName("id")
    var id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("ingredients")
    val ingredients: List<Ingredient>,
    @SerialName("method")
    val method: List<String>,
    @SerialName("imageUrl")
    val imageUrl: String,
) : Parcelable
