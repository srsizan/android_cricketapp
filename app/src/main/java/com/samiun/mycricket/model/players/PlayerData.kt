package com.samiun.mycricket.model.players

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "players")
data class PlayerData(
    val fullname: String?,
    @PrimaryKey
    val id: Int?,
    val image_path: String?,
    val resource: String?,
    val dateofbirth: String?,
    val updated_at: String?


): Parcelable