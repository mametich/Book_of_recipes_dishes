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
data class Category(
    @SerialName("id")
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "title")
    @SerialName("title")
    val title: String,
    @SerialName("description")
    @ColumnInfo(name = "description")
    val description: String,
    @SerialName("imageUrl")
    @ColumnInfo(name = "imageUrl")
    val imgUrl: String,
) : Parcelable
