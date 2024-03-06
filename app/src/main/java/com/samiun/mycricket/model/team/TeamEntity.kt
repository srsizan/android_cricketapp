package com.samiun.mycricket.model.team

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")

data class TeamEntity(
    val code: String?,
    val country_id: Int?,
    @PrimaryKey
    val id: Int?,
    val image_path: String?,
    val name: String?,
    val national_team: Boolean?,
    val resource: String?,
    val updated_at: String?
)