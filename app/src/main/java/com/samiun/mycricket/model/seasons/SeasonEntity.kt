package com.samiun.mycricket.model.seasons

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leagues")

data class SeasonEntity(
    val code: String,
    @PrimaryKey
    val id: Int,
    val league_id: Int,
    val name: String,
    val resource: String,
    val updated_at: String
)