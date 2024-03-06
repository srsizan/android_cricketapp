package com.samiun.mycricket.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.model.teamDetails.Result
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.utils.MyApplication
import kotlinx.android.synthetic.main.match_list.view.*
import kotlinx.coroutines.*

class TeamResultsAdapter(
    private val viewModel: CricketViewModel,
    private var arrayList: List<Result>
)
    : RecyclerView.Adapter<TeamResultsAdapter.TeamResultsViewHolder>(){
    class TeamResultsViewHolder(
        binding: View
    ): RecyclerView.ViewHolder(binding){
        val homeTeamName = itemView.home_team
        val awayTeamName = itemView.away_team
        val hometeamImage = itemView.home_toss
        val awayteamImage = itemView.away_toss
        val notes = itemView.match_notes
        val cardView = itemView.constraint_item

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamResultsViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.match_list,parent,false)
        return TeamResultsViewHolder(root)
    }
    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: TeamResultsViewHolder, position: Int) {
        val match = arrayList[position]
        holder.notes.text = match.note
        Log.e("Fixture with run", "onBindViewHolder:${match.id} ", )
        GlobalScope.launch {
            val hometeam = match.localteam_id?.let { viewModel.findTeamById(it) }
            val awayteam = match.visitorteam_id?.let { viewModel.findTeamById(it) }

            withContext(Dispatchers.Main) {
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
                    holder.homeTeamName.text = hometeam.code

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

            holder.cardView.setOnClickListener {
            }
        }
    }

    override fun getItemCount(): Int {
        return  arrayList.size
    }

}