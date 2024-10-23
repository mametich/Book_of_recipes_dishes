package com.example.burgershop.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity
data class Recipe(
    @SerialName("id")
    @PrimaryKey
    var id: Int,

    @SerialName("title")
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "categoryId")
    val categoryId: Int = -1,

    @ColumnInfo(name = "favorites")
    var isFavorites: Boolean = false,

    @SerialName("ingredients")
    @ColumnInfo(name = "ingredients")
    val ingredients: List<Ingredient>,

    @SerialName("method")
    @ColumnInfo(name = "method")
    val method: List<String>,

    @SerialName("imageUrl")
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
) : Parcelable
