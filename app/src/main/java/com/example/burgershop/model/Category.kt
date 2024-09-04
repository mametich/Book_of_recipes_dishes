package com.example.burgershop.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Category(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("imgUrl")
    val imgUrl: String,
) : Parcelable

@Serializable
data class Response(
    val listOfCategory: List<Category>
)
