package com.samiun.mycricket.model.ranking

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.android.parcel.RawValue

@Entity(tableName = "ranking", primaryKeys = ["gender", "type"])
data class RankingData(
    var gender: String,
    @TypeConverters
    var points:@RawValue Any?,
    @TypeConverters
    val position:@RawValue Any?,
    @TypeConverters
    var rating:@RawValue Any?,
    var resource: String?,
    @TypeConverters
    var team:@RawValue List<Team>?,
    var type: String,
    var updated_at: String?
)
