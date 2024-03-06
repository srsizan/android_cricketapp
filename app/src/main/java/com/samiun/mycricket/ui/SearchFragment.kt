package com.samiun.mycricket.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.SearchPlayerAdapter
import com.samiun.mycricket.adapter.SearchTeamAdapter
import com.samiun.mycricket.databinding.FragmentSearchBinding
import com.samiun.mycricket.model.players.PlayerData
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.network.overview.CricketViewModel


class SearchFragment : Fragment() {

    private lateinit var viewModel: CricketViewModel
    private lateinit var teamList: List<TeamEntity>
    private lateinit var playerList: List<PlayerData>
    var isTeamList = true

    private lateinit var searchRecyclerview: RecyclerView
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        searchRecyclerview = binding.searchRv


        viewModel.readTeamEntity.observe(viewLifecycleOwner){
            val adapterViewState = searchRecyclerview.layoutManager?.onSaveInstanceState()
            searchRecyclerview.layoutManager?.onRestoreInstanceState(adapterViewState)
            searchRecyclerview.adapter = SearchTeamAdapter(it!!)
            teamList = it

        }

        binding.teamSearch.setOnClickListener {
            viewModel.readTeamEntity.observe(viewLifecycleOwner){
                val adapterViewState = searchRecyclerview.layoutManager?.onSaveInstanceState()
                searchRecyclerview.layoutManager?.onRestoreInstanceState(adapterViewState)
                searchRecyclerview.adapter = SearchTeamAdapter(it!!)
                teamList = it
                isTeamList = true
            }
        }

        binding.playerSearch.setOnClickListener {
            viewModel.readPlayerData.observe(viewLifecycleOwner){
                val adapterViewState = searchRecyclerview.layoutManager?.onSaveInstanceState()
                searchRecyclerview.layoutManager?.onRestoreInstanceState(adapterViewState)
                searchRecyclerview.adapter =SearchPlayerAdapter(it!!)
                playerList = it
                isTeamList = false
            }
        }

        binding.bottomNav?.let {
            it.menu.getItem(2).isChecked = true
        }

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment->{

                    findNavController().navigate(R.id.homeFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.rankingFragment->{
                    findNavController().navigate(R.id.rankingFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment->{
                    return@setOnItemSelectedListener true
                }
                else ->{
                    findNavController().navigate(R.id.seriesFragment)
                    return@setOnItemSelectedListener true
                }
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.action_search_btn)
        val searchview = item?.actionView as SearchView
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (searchRecyclerview.adapter != null) {
                    if(isTeamList){
                        try {
                            val teamAdapter = searchRecyclerview.adapter as SearchTeamAdapter
                            if (newText.length > 1) {
                                teamAdapter.filter(newText)
                            } else teamAdapter.updateList(teamList)
                        }
                        catch (e: Exception){
                            Log.e(TAG, "onQueryTextChange: $e", )
                        }

                    }
                    else {
                        try {
                            val teamAdapter = searchRecyclerview.adapter as SearchPlayerAdapter
                            if (newText.length > 1) {
                                teamAdapter.filter(newText)
                            } else teamAdapter.updateList(playerList)
                        }
                        catch (e:Exception){
                            Log.e(TAG, "onQueryTextChange: $e", )

                        }

                    }
                }
                return true
            }
        })


        super.onCreateOptionsMenu(menu, inflater)
    }

}