package com.samiun.mycricket.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.LiveMatchAdapter
import com.samiun.mycricket.adapter.RecentMatchAdapter
import com.samiun.mycricket.adapter.UpcomingMatchAdapter
import com.samiun.mycricket.databinding.FragmentHomeBinding
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.utils.WinningPercentage

private lateinit var topviewModel: CricketViewModel

class HomeFragment : Fragment() {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var liveMatchAdapter: LiveMatchAdapter
    private lateinit var viewModel: CricketViewModel
    private lateinit var recentRecyclerView:RecyclerView
    private lateinit var upcomingRecyclerView: RecyclerView
    private lateinit var liveRecyclerView: RecyclerView

    private var _binding: FragmentHomeBinding? = null
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

        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Remove any pending runnables from the handler to avoid memory leaks
        handler.removeCallbacksAndMessages(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        topviewModel = viewModel

        recentRecyclerView = binding.recentMatchesRv

        liveRecyclerView = binding.liveMatchesRv

        liveMatchAdapter = LiveMatchAdapter(requireContext(), viewModel, emptyList())
        liveRecyclerView.adapter = liveMatchAdapter
        handler.postDelayed(object : Runnable {
            override fun run() {
                viewModel.getLiveMatch().observe(viewLifecycleOwner) { liveMatchList ->
                    liveMatchAdapter.updateData(liveMatchList)
                    if(liveMatchList.isEmpty()){
                        binding.noLiveMatches.visibility = view.visibility
                    }

                    // Schedule the function to be executed again after 1 minute
                    handler.postDelayed(this, 60000)
                }
            }
        }, 0)

/*        viewModel.getLiveMatch().observe(viewLifecycleOwner){
            val adapterViewState = liveRecyclerView.layoutManager?.onSaveInstanceState()
            liveRecyclerView.layoutManager?.onRestoreInstanceState(adapterViewState)
            liveRecyclerView.adapter = LiveMatchAdapter(requireContext(), viewModel, it)
        }*/
        viewModel.readFixtureWithRunEntity.observe(viewLifecycleOwner){
            val adapterViewState = recentRecyclerView.layoutManager?.onSaveInstanceState()
            recentRecyclerView.layoutManager?.onRestoreInstanceState(adapterViewState)
            recentRecyclerView.adapter = RecentMatchAdapter(
                viewModel,
                viewModel.readFixtureWithRunEntity.value!!
            )
        }

        upcomingRecyclerView = binding.upcomingMatchesRv
        viewModel.readFixtureEntity.observe(viewLifecycleOwner){
            val adapterViewState = upcomingRecyclerView.layoutManager?.onSaveInstanceState()
            upcomingRecyclerView.layoutManager?.onRestoreInstanceState(adapterViewState)
            upcomingRecyclerView.adapter = UpcomingMatchAdapter(requireContext(), viewModel, viewModel.readFixtureEntity.value!!)
        }
        val percentage = calculateBangladeshWinningPercentage()
        Log.e("HomeFragment winning percentage", "onViewCreated: $percentage", )

        binding.bottomNav?.let {
            it.menu.getItem(0).isChecked = true
        }

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeFragment->{

                    return@setOnItemSelectedListener true
                }
                R.id.rankingFragment->{
                    findNavController().navigate(R.id.rankingFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment->{
                    findNavController().navigate(R.id.searchFragment)
                    return@setOnItemSelectedListener true
                }
                else ->{
                    findNavController().navigate(R.id.seriesFragment)
                    return@setOnItemSelectedListener true
                }
            }

        }
    }
    fun getArticlesBackgroud() {
        topviewModel.getCountries()
        topviewModel.getLeagues()
        topviewModel.getFixtures()
        topviewModel.getTeams()
        topviewModel.getFixturesWithRun()
        topviewModel.getRanking()
        topviewModel.getPlayers()
        topviewModel.getOfficials()
        topviewModel.getVenus()
        topviewModel.getLiveMatch()

    }

    fun calculateBangladeshWinningPercentage(): Double {
        val indiaRating = 239
        val bangladeshRating = 176
        val indiaOverallWinningPercentage = 0.79
        val bangladeshOverallWinningPercentage = 0.56
        val bangladeshHomeWinningPercentage = 0.65
        val indiaAwayWinningPercentage = 0.47

        val indiaScore = 50
        val indiaWickets = 4
        val indiaOvers = 9
        val indiaRunRate = indiaScore.toDouble() / (indiaOvers+indiaWickets)

        val bangladeshRunRate = indiaRunRate * (bangladeshRating.toDouble() / indiaRating.toDouble())
        val bangladeshExpectedScore = bangladeshRunRate * indiaOvers
        val bangladeshTarget = bangladeshExpectedScore + 1

        val indiaChances = indiaOverallWinningPercentage * indiaAwayWinningPercentage
        val bangladeshChances = bangladeshOverallWinningPercentage * bangladeshHomeWinningPercentage

        val bangladeshWinningPercentage = (bangladeshChances) / (bangladeshChances + indiaChances) * 100

        return bangladeshWinningPercentage
    }


}