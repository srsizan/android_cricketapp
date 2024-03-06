package com.samiun.mycricket.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.samiun.mycricket.model.fixturewithrun.Run

class RunsConverter {
    @TypeConverter
    fun fromString(value: String): List<Run>? {
        val type = object : TypeToken<List<Run>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromList(list: List<Run>?): String {
        return Gson().toJson(list)
    }
}