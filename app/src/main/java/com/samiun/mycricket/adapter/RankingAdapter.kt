package com.samiun.mycricket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.model.ranking.Team
import com.samiun.mycricket.ui.RankingFragmentDirections
import com.samiun.mycricket.utils.MyApplication
import kotlinx.android.synthetic.main.ranking_list.view.*

class RankingAdapter(private var data: List<Team>)
    : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>(){
    class RankingViewHolder(
        binding: View
    ): RecyclerView.ViewHolder(binding){
        val teamRank = itemView.rank_position
        val teamImage = itemView.ranking_image
        val teamname = itemView.team_name
        val rating = itemView.rating
        val item = itemView.ranking_item

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.ranking_list,parent,false)
        return RankingViewHolder(root)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val info = data[position]
        info.ranking
        holder.teamRank.text = info.position.toString()
        holder.teamname.text = info.name
        holder.rating.text = info.ranking.rating.toString()
        Glide
            .with(MyApplication.instance)
            .load(info.image_path)
            .placeholder(R.drawable.image_downloading)
            .error(R.drawable.not_found_image)
            .into(holder.teamImage)

        holder.item.setOnClickListener {
            val action = RankingFragmentDirections.actionRankingFragmentToTeamFragment(info.id)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}