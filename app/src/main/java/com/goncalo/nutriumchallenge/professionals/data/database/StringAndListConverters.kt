package com.goncalo.nutriumchallenge.professionals.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringAndListConverters {

    @TypeConverter
    fun fromString(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type) ?: emptyList()
    }

    @TypeConverter
    fun toString(value: List<String>) = Gson().toJson(value)

}