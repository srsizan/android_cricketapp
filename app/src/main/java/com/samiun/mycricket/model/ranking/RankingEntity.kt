package com.samiun.mycricket.model.ranking

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.RawValue

class RankingEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int ,
    val gender: String,
    val points:@RawValue Any,
    val position:@RawValue Any,
    val rating:@RawValue Any,
    val resource: String,
    val team:@RawValue List<Team>,
    val type: String,
    val updated_at: String
)