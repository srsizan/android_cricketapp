package com.samiun.mycricket.model.venue

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "venues")

data class VenueEntity(
    val capacity: Int?,
    val city: String?,
    val country_id: Int?,
    val floodlight: Boolean?,
    @PrimaryKey
    val id: Int?,
    val image_path: String?,
    val name: String?,
    val resource: String?,
    val updated_at: String?
)