package com.samiun.mycricket.adapter

import android.app.Application
import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRun
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRunEntity
import com.samiun.mycricket.model.teamDetails.TeamDetailsData
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.ui.HomeFragmentDirections
import com.samiun.mycricket.utils.Constants
import com.samiun.mycricket.utils.MyApplication
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.match_list.view.*
import kotlinx.coroutines.*
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

class UpcomingMatchAdapter(private val context: Context, private val viewModel: CricketViewModel,private var arrayList: List<FixtureEntity>)
    :RecyclerView.Adapter<UpcomingMatchAdapter.UpcomingMatchViewHolder>(){
    class UpcomingMatchViewHolder(
        binding: View
    ): RecyclerView.ViewHolder(binding){
        val homeTeamName = itemView.home_team
        val awayTeamName = itemView.away_team
        val hometeamImage = itemView.home_toss
        val awayteamImage = itemView.away_toss
        val notes = itemView.match_notes
        val item = itemView.constraint_item
        val homescore = itemView.home_team_score
        val awayscore = itemView.away_team_score
        val status = itemView.isUpcoming


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMatchViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.match_list,parent,false)
        return UpcomingMatchViewHolder(root)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: UpcomingMatchViewHolder, position: Int) {
        val match = arrayList[position]
        holder.notes.text = match.starting_at?.let { Constants.upcomingtimeFormat(it) }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val targetDate = dateFormat.parse(match.starting_at)
        holder.status.visibility = View.VISIBLE


        holder.item.setOnClickListener {

            val fixtureWithRun = FixtureWithRunEntity(match.draw_noresult,match.elected,match.first_umpire_id,match.follow_on,match.id,match.last_period,match.league_id,match.live,null,match.localteam_id,match.man_of_match_id,match.man_of_series_id,match.note,match.referee_id,match.resource,
            match.round,match.rpc_overs,match.rpc_target,null,match.season_id,match.second_umpire_id,match.stage_id,match.starting_at,match.status,match.super_over,match.toss_won_team_id,match.total_overs_played,match.tv_umpire_id,match.type,match.venue_id,null,match.visitorteam_id,match.weather_report,match.winner_team_id)
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(fixtureWithRun)
            holder.itemView.findNavController().navigate(action)
        }

        match.starting_at



        GlobalScope.launch {
            val hometeam = match.localteam_id?.let { viewModel.findTeamById(it) }
            val awayteam = match.visitorteam_id?.let { viewModel.findTeamById(it) }

            withContext(Dispatchers.Main) {

                if (hometeam != null) {

                    if(hometeam.name!!.length>15){
                        holder.homeTeamName.text = hometeam.code
                    }
                    else
                        holder.homeTeamName.text = hometeam.name




                    Glide
                        .with(MyApplication.instance)
                        .load(hometeam.image_path)
                        .placeholder(R.drawable.image_downloading)
                        .error(R.drawable.not_found_image)
                        .into(holder.hometeamImage)
                } else {
                    val id = match.id.toString()
                    holder.homeTeamName.text = id
                    Log.e("Adapter", "${match.id} ", )
                }

                if (awayteam != null) {
                    if(awayteam.name!!.length>15){
                        holder.awayTeamName.text = awayteam.code
                    }
                    else
                        holder.awayTeamName.text = awayteam.name
                    Glide
                        .with(MyApplication.instance)
                        .load(awayteam.image_path)
                        .placeholder(R.drawable.image_downloading)
                        .error(R.drawable.not_found_image)
                        .into(holder.awayteamImage)
                } else {
                    val id = match.id.toString()
                    holder.awayTeamName.text = id
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return  arrayList.size
    }

}