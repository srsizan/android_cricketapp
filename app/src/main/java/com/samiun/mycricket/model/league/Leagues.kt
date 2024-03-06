package com.samiun.mycricket.model.league

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samiun.mycricket.utils.Constants

@Entity(tableName = "leagues")
data class Leagues(
    val code: String,
    val country_id: Int,
    @PrimaryKey
    val id: Int,
    val image_path: String,
    val name: String,
    val resource: String,
    val season_id: Int,
    val type: String,
    val updated_at: String
)