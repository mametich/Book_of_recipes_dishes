package com.example.burgershop.data

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MethodConverter {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromMethod(listOfMethod: List<String>) : String {
        return json.encodeToString(listOfMethod)
    }

    fun toMethod(methodString: String) : List<String> {
        return json.decodeFromString(methodString)
    }
}