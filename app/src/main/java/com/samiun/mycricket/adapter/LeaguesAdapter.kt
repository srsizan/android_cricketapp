package com.samiun.mycricket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.model.league.Leagues
import com.samiun.mycricket.ui.SeriesHomeDirections
import com.samiun.mycricket.utils.MyApplication
import kotlinx.android.synthetic.main.ranking_list.view.*

class LeaguesAdapter(private var data: List<Leagues>)
    : RecyclerView.Adapter<LeaguesAdapter.LeaguesViewHolder>(){
    class LeaguesViewHolder(
        binding: View
    ): RecyclerView.ViewHolder(binding){
        val teamRank = itemView.rank_position
        val leagueImage = itemView.ranking_image
        val leaguename = itemView.team_name
        val rating = itemView.rating
        val item = itemView.ranking_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaguesViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.ranking_list,parent,false)
        return LeaguesViewHolder(root)
    }

    override fun onBindViewHolder(holder: LeaguesViewHolder, position: Int) {
        val info = data[position]
        holder.teamRank.visibility = View.GONE
        holder.leaguename.text = info.name
        Glide
            .with(MyApplication.instance)
            .load(info.image_path)
            .placeholder(R.drawable.image_downloading)
            .error(R.drawable.not_found_image)
            .into(holder.leagueImage)

        holder.item.setOnClickListener {
            val action = SeriesHomeDirections.actionSeriesFragmentToSeriesDetailsFragment(info.id)
            holder.itemView.findNavController().navigate(action)
        }
    }
    override fun getItemCount(): Int {
        return data.size

    }

}