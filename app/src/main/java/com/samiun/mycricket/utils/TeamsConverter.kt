package com.samiun.mycricket.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.samiun.mycricket.model.fixturewithrun.Run
import com.samiun.mycricket.model.ranking.Team

class TeamsConverter {
    @TypeConverter
    fun fromString(value: String): List<Team>? {
        val type = object : TypeToken<List<Team>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromList(list: List<Team>?): String {
        return Gson().toJson(list)
    }
}