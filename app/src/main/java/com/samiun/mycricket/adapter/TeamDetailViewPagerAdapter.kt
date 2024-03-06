package com.samiun.mycricket.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.samiun.mycricket.model.teamDetails.TeamDetailsData
import com.samiun.mycricket.ui.team.TeamResutlsFragment
import com.samiun.mycricket.ui.team.TeamScheduleFragment
import com.samiun.mycricket.ui.team.TeamSquadFragment
import com.samiun.mycricket.ui.team.TeamStatsFragment


class TeamDetailViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val adapterData: TeamDetailsData?
): FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        Bundle()
        when(position){
            0->{
                val fragment = TeamScheduleFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable("teamdetails",adapterData)
                }
                return fragment
            }
            1-> {
                val fragment = TeamResutlsFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable("teamdetails",adapterData)
                }
                return fragment
            }
            2->{
                val fragment = TeamSquadFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable("teamdetails",adapterData)
                }
                return fragment
            }
            else ->{
                val fragment = TeamStatsFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable("teamdetails",adapterData)
                }
                return fragment
            }

        }
    }
}
