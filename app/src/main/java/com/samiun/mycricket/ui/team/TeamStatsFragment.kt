package com.samiun.mycricket.ui.team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.SearchPlayerAdapter
import com.samiun.mycricket.databinding.FragmentTeamSquadBinding
import com.samiun.mycricket.databinding.FragmentTeamStatsBinding
import com.samiun.mycricket.model.players.PlayerData
import com.samiun.mycricket.model.teamsquad.TeamSquadData
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.utils.Constants.Companion.awaydata
import com.samiun.mycricket.utils.Constants.Companion.homedata
import com.samiun.mycricket.utils.Constants.Companion.totalOtherWon
import com.samiun.mycricket.utils.Constants.Companion.totalTeamWon
import kotlinx.android.synthetic.main.fragment_team_stats.*


class TeamStatsFragment : Fragment() {

    private lateinit var viewModel: CricketViewModel
    val gerArgs: TeamStatsFragmentArgs by navArgs()
    private var _binding: FragmentTeamStatsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeamStatsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = gerArgs.teamdetails

        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        val results = data.results

        val totalmatches = results?.size
        val totalteamwon = totalTeamWon(results,data.id)
        val totalOtherwon = totalOtherWon(results,data.id)
        var totalPercentage: Double
        try {
            totalPercentage=(totalteamwon.toDouble()/ (totalmatches?.toDouble()!!)) *100

        }
        catch (e: Exception){
            totalPercentage = 0.0
        }

        binding.total.text = "Total\n($totalmatches)"
        binding.totalteamwon.text = "$totalteamwon"
        binding.totalotherwon.text = "$totalOtherwon"
        binding.totalpercentage.text = "$totalPercentage"

        val homeData = data.id?.let { homedata(results, it) }
        val awayData = data.id?.let { awaydata(results, it) }

        if (homeData != null) {
            binding.home.text = "Home\n(${homeData.get(0)})"
            binding.hometeamwon.text = "${homeData.get(1)}"
            binding.homeotherwon.text = "${homeData.get(2)}"
            val percentage = homeData.get(3)
            binding.homepercentage.text = "%.2f".format(percentage)
        }
        if (awayData != null) {
            binding.away.text = "Away\n(${awayData.get(0)})"
            binding.awayteamwon.text = "${awayData.get(1)}"
            binding.awayotherwon.text = "${awayData.get(2)}"
            val percentage = awayData.get(3)
            binding.awaypercentage.text = "%.2f".format(percentage)
        }



    }
}