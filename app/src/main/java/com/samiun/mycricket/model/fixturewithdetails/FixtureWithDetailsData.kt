package com.samiun.mycricket.model.fixturewithdetails

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize

data class FixtureWithDetailsData(
    var balls:@RawValue List<Ball>?,
    var batting:@RawValue List<Batting>?,
    var bowling:@RawValue List<Bowling>?,
    var draw_noresult:@RawValue Any?,
    var elected: String?,
    var first_umpire_id: Int?,
    var follow_on: Boolean?,
    var id: Int?,
    var last_period:@RawValue Any?,
    var league_id: Int?,
    var lineup:@RawValue List<Lineup>?,
    var live: Boolean?,
    var localteam_dl_data:@RawValue LocalteamDlData?,
    var localteam_id: Int?,
    var man_of_match_id: Int?,
    var man_of_series_id:@RawValue Any?,
    var note: String?,
    var referee_id: Int?,
    var resource: String?,
    var round: String?,
    var rpc_overs:@RawValue Any?,
    var rpc_target:@RawValue Any?,
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
    var visitorteam_dl_data:@RawValue VisitorteamDlData?,
    var visitorteam_id: Int?,
    var weather_report:@RawValue List<Any>?,
    var winner_team_id: Int?
): Parcelable{

        constructor():this(
        null,
        null,
        null,
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
        null
        )
}