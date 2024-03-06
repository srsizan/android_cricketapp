package com.samiun.mycricket.model.fixture

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Fixtures")

data class FixtureEntity(
    var draw_noresult: String?,
    var elected: String?,
    var first_umpire_id: Int?,
    var follow_on: Boolean?,
    @PrimaryKey
    var id: Int?,
   @Ignore
    var last_period: Any?,
    var league_id: Int?,
    var live: Boolean?,
    @Ignore
    var localteam_dl_data: LocalteamDlData?,
    var localteam_id: Int?,
    var man_of_match_id: Int?,
    var man_of_series_id: Int?,
    var note: String?,
    var referee_id: Int?,
    var resource: String?,
    var round: String?,
    var rpc_overs: String?,
    var rpc_target: String?,
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
    var visitorteam_dl_data: VisitorteamDlData?,
    var visitorteam_id: Int?,
    @Ignore
    var weather_report: List<Any>?,
    var winner_team_id: Int?
){
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
  null)
}