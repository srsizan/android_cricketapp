package com.samiun.mycricket.ui.team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.adapter.SearchPlayerAdapter
import com.samiun.mycricket.databinding.FragmentTeamSquadBinding
import com.samiun.mycricket.model.players.PlayerData
import com.samiun.mycricket.model.teamsquad.TeamSquadData
import com.samiun.mycricket.network.overview.CricketViewModel

class TeamSquadFragment : Fragment() {

    private lateinit var viewModel: CricketViewModel
    val gerArgs: TeamSquadFragmentArgs by navArgs()
    private var _binding: FragmentTeamSquadBinding? = null
    private lateinit var teamSquadRecyclerview: RecyclerView

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeamSquadBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = gerArgs.teamdetails
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        teamSquadRecyclerview = binding.squadRv
        viewModel.getTeamSquad(data.id!!).observe(viewLifecycleOwner){
            val adapterViewState = teamSquadRecyclerview.layoutManager?.onSaveInstanceState()
            teamSquadRecyclerview.layoutManager?.onRestoreInstanceState(adapterViewState)
            teamSquadRecyclerview.adapter = SearchPlayerAdapter(squadtoPlayer(it))
        }

    }


    fun squadtoPlayer(teamSquadData: TeamSquadData): List<PlayerData>{
        val list = teamSquadData.squad!!
        return  list.map {
            PlayerData(
                it.fullname,
                it.id,
                it.image_path,
                it.resource,
                it.dateofbirth,
                it.updated_at
            )
        }
    }
}