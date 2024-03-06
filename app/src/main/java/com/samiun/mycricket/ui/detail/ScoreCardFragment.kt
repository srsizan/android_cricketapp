package com.samiun.mycricket.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.adapter.BattingCardAdapter
import com.samiun.mycricket.adapter.BowlingCardAdapter
import com.samiun.mycricket.databinding.FragmentScoreCardBinding
import com.samiun.mycricket.model.fixturewithdetails.Batting
import com.samiun.mycricket.model.fixturewithdetails.Bowling
import com.samiun.mycricket.model.fixturewithdetails.Lineup
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.network.overview.CricketViewModel
import kotlinx.android.parcel.RawValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScoreCardFragment : Fragment() {

    val gerArgs: ScoreCardFragmentArgs by navArgs()
    private var _binding: FragmentScoreCardBinding? = null
    private val binding get() = _binding!!
    private lateinit var battingRV : RecyclerView
    private lateinit var bowlingRV: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScoreCardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val  viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        battingRV = binding.battingRv
        bowlingRV = binding.bowlingRv

        val battingFirstBtn = binding.battingfirstScoreBtn
        val bowlingFirstBtn = binding.bowlingfirstScoreBtn


        val data = gerArgs.matchdetails
        var battingFirstTeam: TeamEntity
        var bowlingFirstTeam: TeamEntity
        val lineup = data.lineup
        val batting = data.batting
        val bowling = data.bowling
        val battingFirstBatting : MutableList<Batting> = mutableListOf<Batting>()
        val bowlingFirstBatting : MutableList<Batting> = mutableListOf<Batting>()
        val battingFirstBowling : MutableList<Bowling> = mutableListOf<Bowling>()
        val bowlingFirstBowling : MutableList<Bowling> = mutableListOf<Bowling>()
        var battingFirstId: Int=0
        var bowlingFirstId : Int=0
        var isBattingFirstBatting = true

        if((data.localteam_id==data.toss_won_team_id && data.elected =="batting")||(data.visitorteam_id==data.toss_won_team_id && data.elected =="bowling")){
            battingFirstId = data.localteam_id!!
            bowlingFirstId  = data.visitorteam_id!!
        }
        else{
            bowlingFirstId = data.localteam_id!!
            battingFirstId  = data.visitorteam_id!!
        }





        GlobalScope.launch {
            battingFirstTeam = viewModel.findTeamById(battingFirstId)
            bowlingFirstTeam= viewModel.findTeamById(bowlingFirstId)

            withContext(Dispatchers.Main){
                battingFirstBtn.text = battingFirstTeam.code
                bowlingFirstBtn.text = bowlingFirstTeam.code

            }

        }
        for(i in lineup!!){
            for(j in batting!!){
                if(j.player_id == i.id && i.lineup!!.team_id==battingFirstId){
                    battingFirstBatting.add(j)
                }
                else if(j.player_id == i.id && i.lineup!!.team_id==bowlingFirstId){
                    bowlingFirstBatting.add(j)
                }
            }
        }

        for(i in lineup!!){
            for(j in bowling!!){
                if(j.player_id == i.id && i.lineup!!.team_id==battingFirstId){
                    battingFirstBowling.add(j)
                }
                else if(j.player_id == i.id && i.lineup!!.team_id==bowlingFirstId){
                    bowlingFirstBowling.add(j)

                }
            }
        }

        battingAdapter(viewModel,battingFirstBatting,lineup)
        bowlingAdapter(viewModel,bowlingFirstBowling,lineup)

        binding.battingfirstScoreBtn.setOnClickListener{
            battingAdapter(viewModel, battingFirstBatting, lineup)
            bowlingAdapter(viewModel,bowlingFirstBowling,lineup)

        }
        binding.bowlingfirstScoreBtn.setOnClickListener {
            battingAdapter(viewModel,bowlingFirstBatting, lineup)
            bowlingAdapter(viewModel,battingFirstBowling,lineup)
        }

    }

    private fun bowlingAdapter(
        viewModel: CricketViewModel,
        bowling: MutableList<Bowling>,
        lineup: @RawValue List<Lineup>
    ) {
        val adapterViewState = bowlingRV.layoutManager?.onSaveInstanceState()
        bowlingRV.layoutManager?.onRestoreInstanceState(adapterViewState)
        bowlingRV.adapter = BowlingCardAdapter(bowling, lineup)
    }

    private fun battingAdapter(
        viewModel: CricketViewModel,
        batting: MutableList<Batting>,
        lineup: @RawValue List<Lineup>
    ) {
        val adapterViewState = battingRV.layoutManager?.onSaveInstanceState()
        battingRV.layoutManager?.onRestoreInstanceState(adapterViewState)
        battingRV.adapter = BattingCardAdapter(viewModel, batting, lineup)
    }
}