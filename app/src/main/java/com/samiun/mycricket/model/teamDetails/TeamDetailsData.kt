package com.samiun.mycricket.model.teamDetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
@Parcelize
data class TeamDetailsData(
    val code: String?,
    val country:@RawValue Country?,
    val country_id: Int?,
    val fixtures:@RawValue List<Fixture>?,
    val id: Int?,
    val image_path: String?,
    val name: String?,
    val national_team: Boolean?,
    val resource: String?,
    val results:@RawValue List<Result>?,
    val squad:@RawValue List<Squad>?,
    val updated_at: String?
): Parcelable