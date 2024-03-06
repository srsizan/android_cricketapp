package com.samiun.mycricket.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.model.fixturewithdetails.Lineup
import com.samiun.mycricket.ui.DetailsFragmentDirections
import com.samiun.mycricket.utils.MyApplication
import kotlinx.android.synthetic.main.lineup_list.view.*

class LineupAdapter(private var data: List<Lineup>)
    : RecyclerView.Adapter<LineupAdapter.LineupViewHolder>(){
    class LineupViewHolder(
        binding: View
    ): RecyclerView.ViewHolder(binding){
        val image = itemView.lineUpImage
        val name = itemView.lineupname
        val item = itemView.constraint_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineupViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R .layout.lineup_list,parent,false)
        return LineupViewHolder(root)
    }

    override fun onBindViewHolder(holder: LineupViewHolder, position: Int) {
        val info = data[position]
        holder.name.text = info.fullname
        Glide
            .with(MyApplication.instance)
            .load(info.image_path)
            .placeholder(R.drawable.image_downloading)
            .error(R.drawable.not_found_image)
            .into(holder.image)

        holder.item.setOnClickListener {
            val action = info.id?.let { it1 ->
                DetailsFragmentDirections.actionDetailsFragmentToPlayerFragment2(
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

}