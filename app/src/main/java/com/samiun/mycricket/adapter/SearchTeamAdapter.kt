package com.samiun.mycricket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.ui.SearchFragmentDirections
import com.samiun.mycricket.utils.MyApplication
import kotlinx.android.synthetic.main.ranking_list.view.*
import kotlinx.android.synthetic.main.ranking_list.view.rating
import java.util.*

class SearchTeamAdapter(private var data: List<TeamEntity>)
    : RecyclerView.Adapter<SearchTeamAdapter.SearchTeamViewHolder>(){
    class SearchTeamViewHolder(
        binding: View
    ): RecyclerView.ViewHolder(binding){
        val teamRank = itemView.rank_position
        val teamImage = itemView.ranking_image
        val teamname = itemView.team_name
        val rating = itemView.rating
        val item = itemView.ranking_item

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTeamViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.ranking_list,parent,false)
        return SearchTeamViewHolder(root)
    }

    override fun onBindViewHolder(holder: SearchTeamViewHolder, position: Int) {
        val info = data[position]
        holder.teamRank.visibility = View.GONE
        holder.teamname.text = info.name
        Glide
            .with(MyApplication.instance)
            .load(info.image_path)
            .placeholder(R.drawable.image_downloading)
            .error(R.drawable.not_found_image)
            .into(holder.teamImage)

        holder.item.setOnClickListener {
            val action = info.id?.let { it1 ->
                SearchFragmentDirections.actionSearchFragmentToTeamFragment(
                    it1
                )
            }
            if (action != null) {
                holder.itemView.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size

    }

    fun filter(text: String) {
        val filteredList = ArrayList<TeamEntity>()
        for (team in data) {
            if (team.name?.lowercase(Locale.ROOT)?.contains(text.lowercase(Locale.ROOT)) == true) {
                filteredList.add(team)
            }
        }
        updateList(filteredList)
    }

    fun updateList(teamList: List<TeamEntity>) {
        data = teamList
        notifyDataSetChanged()
    }

}