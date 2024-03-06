package com.samiun.mycricket.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.adapter.BallsAdapter
import com.samiun.mycricket.databinding.FragmentBallsBinding
import com.samiun.mycricket.model.fixturewithdetails.Ball
import com.samiun.mycricket.network.overview.CricketViewModel


class BallsFragment : Fragment() {
    val gerArgs: BallsFragmentArgs by navArgs()
    private var _binding: FragmentBallsBinding? = null
    private val binding get() = _binding!!
    private lateinit var ballsRv : RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBallsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = gerArgs.matchdetails
        val  viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        ballsRv = binding.ballsRv
        var battingfirstTeamId: Int?
        var bowlingfirstTeamId: Int?

        if((data.toss_won_team_id==data.localteam_id && data.elected == "bowling")||(data.toss_won_team_id==data.visitorteam_id && data.elected == "batting")){
            battingfirstTeamId = data.visitorteam_id
            bowlingfirstTeamId = data.localteam_id
        }
        else{
            battingfirstTeamId = data.localteam_id
            bowlingfirstTeamId = data.visitorteam_id
        }

        val balls: List<Ball>? = data.balls
        balls?.sortedWith(compareBy({ it.team_id != battingfirstTeamId }, { it.team_id != bowlingfirstTeamId }, { it.ball }))
        val ballsdesc = balls?.reversed()
        val adapterViewState = ballsRv.layoutManager?.onSaveInstanceState()
        ballsRv.layoutManager?.onRestoreInstanceState(adapterViewState)
        ballsRv.adapter = ballsdesc?.let { BallsAdapter(requireContext(), it) }

        viewModel.fixturewithDetails.observe(viewLifecycleOwner){
            if(it!= null){
                Log.d("Balls Fragment", "onViewCreated: $it")
            }
        }
    }

}