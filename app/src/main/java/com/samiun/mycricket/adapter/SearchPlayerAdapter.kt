package com.samiun.mycricket.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.model.players.PlayerData
import com.samiun.mycricket.ui.SearchFragmentDirections
import com.samiun.mycricket.ui.TeamFragmentDirections
import com.samiun.mycricket.utils.MyApplication
import kotlinx.android.synthetic.main.ranking_list.view.*
import java.util.*

class SearchPlayerAdapter(
    private var data: List<PlayerData>
)
    : RecyclerView.Adapter<SearchPlayerAdapter.SearchPlayerViewHolder>(){
    class SearchPlayerViewHolder(
        binding: View
    ): RecyclerView.ViewHolder(binding){
        val teamRank = itemView.rank_position
        val teamImage = itemView.ranking_image
        val teamname = itemView.team_name
        val rating = itemView.rating
        val item = itemView.ranking_item


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPlayerViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.ranking_list,parent,false)
        return SearchPlayerViewHolder(root)
    }

    override fun onBindViewHolder(holder: SearchPlayerViewHolder, position: Int) {
        val info = data[position]
        holder.teamRank.visibility = View.GONE
        holder.teamname.text = info.fullname
        Glide
            .with(MyApplication.instance)
            .load(info.image_path)
            .placeholder(R.drawable.image_downloading)
            .error(R.drawable.not_found_image)
            .into(holder.teamImage)


        holder.item.setOnClickListener {

            try {
                val action = SearchFragmentDirections.actionSearchFragmentToPlayerFragment(info.id!!)
                Log.e("Player Search", "onBindViewHolder: ${info.id}, ${info.fullname}")
                holder.itemView.findNavController().navigate(action)
            }
            catch (e: Exception){
                val action = TeamFragmentDirections.actionTeamFragmentToPlayerFragment(info.id!!)
                Log.e("Player Search", "onBindViewHolder: ${info.id}, ${info.fullname}")
                holder.itemView.findNavController().navigate(action)
            }

        }

    }

    override fun getItemCount(): Int {
        // Log.d("Ranking Adapter", "getItemCount: ${data.size}")
        return data.size

    }

    fun filter(text: String) {
        val filteredList = ArrayList<PlayerData>()
        for (player in data) {
            if (player.fullname?.lowercase(Locale.ROOT)?.contains(text.lowercase(Locale.ROOT)) == true) {
                filteredList.add(player)
            }
        }
        updateList(filteredList)
    }

    fun updateList(teamList: List<PlayerData>) {
        data = teamList
        notifyDataSetChanged()
    }

}