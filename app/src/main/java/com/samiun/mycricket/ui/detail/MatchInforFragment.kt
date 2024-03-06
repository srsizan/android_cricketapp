package com.samiun.mycricket.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.adapter.LineupAdapter
import com.samiun.mycricket.databinding.FragmentMatchInforBinding
import com.samiun.mycricket.model.fixturewithdetails.Lineup
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchInforFragment : Fragment() {

    val gerArgs: MatchInforFragmentArgs by navArgs()
    private var _binding: FragmentMatchInforBinding? = null
    private val binding get() = _binding!!
    private lateinit var localRV : RecyclerView
    private lateinit var visitoreRv : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMatchInforBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = gerArgs.matchdetails
        localRV = binding.localteamSquadRv
        visitoreRv = binding.visitorteamSquadRv
        val localSquad = mutableListOf<Lineup>()
        val visitorSquad =  mutableListOf<Lineup>()
        for(i in data.lineup!!){
            if(i.lineup!!.team_id == data.localteam_id){
                localSquad.add(i)
            }
            else{
                visitorSquad.add(i)
            }
        }

        val adapterViewState = localRV.layoutManager?.onSaveInstanceState()
        localRV.layoutManager?.onRestoreInstanceState(adapterViewState)
        localRV.adapter = LineupAdapter(localSquad)

        val adapterViewState2 = visitoreRv.layoutManager?.onSaveInstanceState()
        visitoreRv.layoutManager?.onRestoreInstanceState(adapterViewState2)
        visitoreRv.adapter = LineupAdapter(visitorSquad)

        binding.dateData.text = data.starting_at?.let { Constants.dateFormat(it) }
        binding.timeData.text = data.starting_at?.let { Constants.timeFormat(it) }
        binding.infoMatchdata.text = data.round
        val  viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        GlobalScope.launch {
            val tossWinner = data.toss_won_team_id?.let { viewModel.findTeamById(it) }
            val firstUmpire = data.first_umpire_id?.let { viewModel.findOfficialbyId(it) }
            val secondUmpire = data.second_umpire_id?.let { viewModel.findOfficialbyId(it) }
            val refree = data.referee_id?.let { viewModel.findOfficialbyId(it) }
            val leagues = data.league_id?.let { viewModel.findLeaguebyId(it) }
            val venue = data.venue_id?.let { viewModel.findVenueById(it) }

            val localTeam = data.localteam_id?.let { viewModel.findTeamById(it) }
            val visitorteam  = data.visitorteam_id?.let { viewModel.findTeamById(it) }

            binding.localteamName.text = localTeam?.name
            binding.visitorteamName.text = visitorteam?.name
            if(data.man_of_match_id!=null){
                binding.momlayout.visibility = View.VISIBLE
                binding.momdata.text = viewModel.findPlayerbyId(data.man_of_match_id!!).fullname
            }

            if(data.man_of_series_id!=null){
                try {
                    binding.moslayout.visibility = View.VISIBLE
                    binding.momdata.text = viewModel.findPlayerbyId(data.man_of_series_id as Int)?.fullname
                }
                catch (e:Exception){
                    Log.e("Match MOS Exception", "onViewCreated: $e", )
                }

            }


            withContext(Dispatchers.Main){


                try{
                    binding.tossData.text = "${tossWinner!!.name} won and Elected ${data.elected}"

                }
                catch (e: Exception){

                }
                if (venue != null) {
                    binding.venuData.text = venue.name
                    binding.stadiumData.text = venue.name
                    binding.cityData.text = venue.city
                }
                if (firstUmpire != null&& secondUmpire!=null) {
                    binding.umpireData.text ="${firstUmpire.fullname}, ${secondUmpire.fullname}"
                }
                if (refree != null) {
                    binding.reffreeData.text = refree.fullname
                }
                if (leagues != null) {
                    binding.seriesData.text = leagues.name
                }



            }

        }


        binding.tossData.text = data.elected



    }


}