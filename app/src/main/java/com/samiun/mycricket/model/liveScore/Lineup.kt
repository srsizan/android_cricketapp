package com.samiun.mycricket.model.liveScore

data class Lineup(
    val battingstyle: String?,
    val bowlingstyle: String?,
    val country_id: Int?,
    val dateofbirth: String?,
    val firstname: String?,
    val fullname: String?,
    val gender: String?,
    val id: Int?,
    val image_path: String?,
    val lastname: String?,
    val lineup: LineupX?,
    val position: Position?,
    val resource: String?,
    val updated_at: String?
)