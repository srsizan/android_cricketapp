package com.samiun. mycricket.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.TeamDetailViewPagerAdapter
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRunEntity
import com.samiun.mycricket.model.liveScore.LiveScoreData
import com.samiun.mycricket.model.teamDetails.TeamDetailsData
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.ui.HomeFragmentDirections
import com.samiun.mycricket.utils.Constants
import com.samiun.mycricket.utils.MyApplication
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.match_list.view.*
import kotlinx.coroutines.*

class LiveMatchAdapter(private val context: Context, private val viewModel: CricketViewModel,private var arrayList: List<LiveScoreData>)
    :RecyclerView.Adapter<LiveMatchAdapter.LiveMatchViewHolder>(){
    class LiveMatchViewHolder(
        binding: View
    ): RecyclerView.ViewHolder(binding){
        val homeTeamName = itemView.home_team
        val awayTeamName = itemView.away_team
        val hometeamImage = itemView.home_toss
        val awayteamImage = itemView.away_toss
        val notes = itemView.match_notes
        val homescore = itemView.home_team_score
        val awayscore = itemView.away_team_score
        val cardView = itemView.constraint_item
        val status = itemView.isLive

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveMatchViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.match_list,parent,false)
        return LiveMatchViewHolder(root)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: LiveMatchViewHolder, position: Int) {
        val match = arrayList[position]
        holder.notes.text = match.note
        val runs = match.runs
        holder.status.visibility = View.VISIBLE
        var locatTeam: TeamDetailsData
        var vistorTeam: TeamDetailsData


        GlobalScope.launch {
            val hometeam = match.localteam_id?.let { viewModel.findTeamById(it) }
            val awayteam = match.visitorteam_id?.let { viewModel.findTeamById(it) }

            withContext(Dispatchers.Main) {

                try {

                    if (hometeam != null) {

                        if(hometeam.id == match.runs?.get(0)?.team_id && match.runs!!.isNotEmpty()) {
                            if(match.runs.size<2){
                                "${match.runs?.get(0)?.score}/${match.runs?.get(0)?.wickets}\n${match.runs?.get(0)?.overs} over".also { holder.homescore.text = it }
                            }
                            else{
                                "${match.runs?.get(0)?.score}/${match.runs?.get(0)?.wickets}\n${match.runs?.get(0)?.overs} over".also { holder.homescore.text = it }
                                "${match.runs?.get(1)?.score}/${match.runs?.get(1)?.wickets}\n${match.runs?.get(1)?.overs} over".also { holder.awayscore.text = it }

                            }
                        }
                        else{
                            if(match.runs?.size ?:0 <2){
                                "${match.runs?.get(1)?.score}/${match.runs?.get(1)?.wickets}\n${match.runs?.get(1)?.overs} over".also { holder.homescore.text = it }
                            }
                            else{
                                "${match.runs?.get(1)?.score}/${match.runs?.get(1)?.wickets}\n${match.runs?.get(1)?.overs} over".also { holder.homescore.text = it }
                                "${match.runs?.get(0)?.score}/${match.runs?.get(0)?.wickets}\n${match.runs?.get(0)?.overs} over".also { holder.awayscore.text = it }
                            }

                            Log.e("Live match", "onBindViewHolder: ${match.runs}", )

                        }

                    }


                }
                catch(e: Exception){
                    Log.e("Live Match Exception", "onBindViewHolder: $e", )
                }


                if (awayteam != null) {
                    holder.awayTeamName.text = awayteam.code
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
                if (hometeam != null) {
                    holder.homeTeamName.text = hometeam!!.code

                    Glide
                        .with(MyApplication.instance)
                        .load(hometeam.image_path)
                        .placeholder(R.drawable.image_downloading)
                        .error(R.drawable.not_found_image)
                        .into(holder.hometeamImage)
                }else {
                    val id = match.id.toString()
                    holder.homeTeamName.text = id
                }

            }


            val fixtureWithRunEntity = FixtureWithRunEntity(match.draw_noresult.toString(),match.elected,match.first_umpire_id,match.follow_on,match.id,match.last_period,match.league_id,match.live,null,match.localteam_id,null,null,match.note,match.referee_id,match.resource,match.round,match.rpc_overs.toString(),match.rpc_target.toString(),match.runs,match.season_id,match.second_umpire_id,match.stage_id,match.starting_at,match.status,match.super_over,match.toss_won_team_id,match.total_overs_played,match.tv_umpire_id,match.type,match.venue_id,null,match.visitorteam_id,match.weather_report,null)
            holder.cardView.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(fixtureWithRunEntity)
                holder.itemView.findNavController().navigate(action)
            }

        }

    }

    override fun getItemCount(): Int {
        return  arrayList.size
    }

    fun updateData(liveMatchList: List<LiveScoreData>?) {

        if (liveMatchList != null) {
            arrayList = liveMatchList
        }
        notifyDataSetChanged()
    }

}