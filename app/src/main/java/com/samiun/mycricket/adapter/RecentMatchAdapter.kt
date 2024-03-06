package com.samiun.mycricket.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRunEntity
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.ui.HomeFragmentDirections
import com.samiun.mycricket.utils.MyApplication
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.fragment_ranking.view.*
import kotlinx.android.synthetic.main.match_list.view.*
import kotlinx.coroutines.*

class RecentMatchAdapter(
    private val viewModel: CricketViewModel,
    private var arrayList: List<FixtureWithRunEntity>
)
    :RecyclerView.Adapter<RecentMatchAdapter.RecentMatchViewHolder>(){
        class RecentMatchViewHolder(
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
            val status = itemView.isRecent

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentMatchViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.match_list,parent,false)
        return RecentMatchViewHolder(root)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: RecentMatchViewHolder, position: Int) {
        val match = arrayList[position]
        holder.notes.text = match.note
        Log.e("Fixture with run", "onBindViewHolder:${match.id} " )
        GlobalScope.launch {
            val hometeam = match.localteam_id?.let { viewModel.findTeamById(it) }
            val awayteam = match.visitorteam_id?.let { viewModel.findTeamById(it) }

            withContext(Dispatchers.Main) {

                try {
                    holder.homescore.text = match.runs?.get(0)?.score.toString()
                    holder.awayscore.text = match.runs?.get(1)?.score.toString()


                    if (hometeam != null) {

                        if(hometeam.id == match.runs?.get(0)?.team_id && match.runs!!.isNotEmpty()) {
                            "${match.runs?.get(0)?.score}/${match.runs?.get(0)?.wickets}\n${match.runs?.get(0)?.overs} over".also { holder.homescore.text = it }
                            "${match.runs?.get(1)?.score}/${match.runs?.get(1)?.wickets}\n${match.runs?.get(1)?.overs} over".also { holder.awayscore.text = it }

                        }
                        else{
                            "${match.runs?.get(1)?.score}/${match.runs?.get(1)?.wickets}\n${match.runs?.get(1)?.overs} over".also { holder.homescore.text = it }
                            "${match.runs?.get(0)?.score}/${match.runs?.get(0)?.wickets}\n${match.runs?.get(0)?.overs} over".also { holder.awayscore.text = it }
                        }

                    }


                }
                catch(e: Exception){
                    Log.e("RecentMatch Adapter Exception", "onBindViewHolder: $e", )
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
            holder.status.visibility = View.VISIBLE
//            holder.status.text = match.status

            holder.cardView.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(match)
                holder.itemView.findNavController().navigate(action)
            }


        }

    }

    override fun getItemCount(): Int {
        return  arrayList.size
    }

}