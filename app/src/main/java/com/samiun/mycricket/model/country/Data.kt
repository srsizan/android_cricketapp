package com.samiun.mycricket.model.country

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samiun.mycricket.utils.Constants

@Entity(tableName = "Countries")
data class Data(
    val continent_id: Int?,
    @PrimaryKey
    val id: Int?,
    val image_path: String?,
    val name: String?,
    val resource: String?,
    val updated_at: String?
)