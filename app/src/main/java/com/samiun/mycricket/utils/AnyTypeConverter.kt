package com.samiun.mycricket.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

class AnyTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): Any? {
        return value?.let {
            gson.fromJson(it, Any::class.java)
        }
    }

    @TypeConverter
    fun toString(value: Any?): String? {
        return value?.let {
            gson.toJson(it)
        }
    }
}