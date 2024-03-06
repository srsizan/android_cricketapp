package com.samiun.mycricket.ui.team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.adapter.TeamResultsAdapter
import com.samiun.mycricket.databinding.FragmentTeamResutlsBinding
import com.samiun.mycricket.network.overview.CricketViewModel


class TeamResutlsFragment : Fragment() {

    private lateinit var viewModel: CricketViewModel
    val gerArgs: TeamResutlsFragmentArgs by navArgs()
    private var _binding: FragmentTeamResutlsBinding? = null
    private lateinit var teamRestulRecyclerview: RecyclerView

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeamResutlsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = gerArgs.teamdetails
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]


        teamRestulRecyclerview = binding.teamRecentMatches
        val adapterViewState = teamRestulRecyclerview.layoutManager?.onSaveInstanceState()
        teamRestulRecyclerview.layoutManager?.onRestoreInstanceState(adapterViewState)
        teamRestulRecyclerview.adapter =  TeamResultsAdapter(viewModel, data.results!!)

    }
}