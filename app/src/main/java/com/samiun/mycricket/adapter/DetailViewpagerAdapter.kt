package com.samiun.mycricket.adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.samiun.mycricket.model.fixturewithdetails.FixtureWithDetailsData
import com.samiun.mycricket.ui.detail.*


class DetailViewpagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val adapterData: FixtureWithDetailsData
): FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        Bundle()
        when(position){
            0->{
                val fragment = BallsFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable("matchdetails",adapterData)
                }
                return fragment
            }
            1-> {
                val fragment = SummeryFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable("matchdetails",adapterData)
                }
                return fragment
            }
            2->{
                val fragment = ScoreCardFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable("matchdetails",adapterData)
                }
                return fragment
            }
            else->{
                val fragment = MatchInforFragment()
                fragment.arguments = Bundle().apply {
                    putParcelable("matchdetails",adapterData)
                }
                return fragment
            }
        }
    }


}
