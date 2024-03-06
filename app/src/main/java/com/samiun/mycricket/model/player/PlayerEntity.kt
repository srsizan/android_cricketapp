package com.samiun.mycricket.model.player

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")

data class PlayerEntity(
    val battingstyle: String?,
    val bowlingstyle: String?,
    val country_id: Int?,
    val dateofbirth: String?,
    val firstname: String?,
    val fullname: String?,
    val gender: String?,
    @PrimaryKey
    val id: Int?,
    val image_path: String?,
    val lastname: String?,
    val position: Position?,
    val resource: String?,
    val updated_at: String?
)