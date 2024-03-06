package com.samiun.mycricket.model.fixturewithrun

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(tableName = "Fixturerun")
data class FixtureWithRunEntity(
    var draw_noresult: String?,
    var elected: String?,
    var first_umpire_id: Int?,
    var follow_on: Boolean?,
    @PrimaryKey
    var id: Int?,
    @Ignore
    var last_period:@RawValue Any?,
    var league_id: Int?,
    var live: Boolean?,
    @Ignore
    var localteam_dl_data: @RawValue LocalteamDlData?,
    var localteam_id: Int?,
    var man_of_match_id: Int?,
    var man_of_series_id: Int?,
    var note: String?,
    var referee_id: Int?,
    var resource: String?,
    var round: String?,
    var rpc_overs: String?,
    var rpc_target: String?,
    @TypeConverters
    var runs:@RawValue List<Run>?,
    var season_id: Int?,
    var second_umpire_id: Int?,
    var stage_id: Int?,
    var starting_at: String?,
    var status: String?,
    var super_over: Boolean?,
    var toss_won_team_id: Int?,
    var total_overs_played: Int?,
    var tv_umpire_id: Int?,
    var type: String?,
    var venue_id: Int?,
    @Ignore
    var visitorteam_dl_data: @RawValue VisitorteamDlData?,
    var visitorteam_id: Int?,
    @Ignore
    var weather_report:@RawValue List<Any>?,
    var winner_team_id: Int?
) : Parcelable {
    constructor():this(
        null,
        null,
        null,
        null,
        0,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
    null)
}