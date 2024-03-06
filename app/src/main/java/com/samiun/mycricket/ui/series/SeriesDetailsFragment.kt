package com.samiun.mycricket.ui.series

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.adapter.*
import com.samiun.mycricket.databinding.FragmentSeriesDetailsBinding
import com.samiun.mycricket.network.overview.CricketViewModel

class SeriesDetailsFragment : Fragment() {
    val getArgs : SeriesDetailsFragmentArgs by navArgs()
    private var _binding: FragmentSeriesDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var seriesRv : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSeriesDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = getArgs.leagueId
        seriesRv = binding.seriesRv
        val  viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        viewModel.findFixuteByLeague(id).observe(viewLifecycleOwner){
            val adapterViewState = seriesRv.layoutManager?.onSaveInstanceState()
            seriesRv.layoutManager?.onRestoreInstanceState(adapterViewState)
            seriesRv.adapter = SeriesRecenMatchAdapter(viewModel, it!!)

        }

        binding.recentseries.setOnClickListener {
            viewModel.findFixuteByLeague(id).observe(viewLifecycleOwner){
                val adapterViewState = seriesRv.layoutManager?.onSaveInstanceState()
                seriesRv.layoutManager?.onRestoreInstanceState(adapterViewState)
                seriesRv.adapter = SeriesRecenMatchAdapter(viewModel, it!!)

            }
        }

        binding.upcomingseries.setOnClickListener {
            viewModel.findUpcomingbyleage(id).observe(viewLifecycleOwner){
                val adapterViewState = seriesRv.layoutManager?.onSaveInstanceState()
                seriesRv.layoutManager?.onRestoreInstanceState(adapterViewState)
                seriesRv.adapter = SeriesUpcomingMatchAdapter(viewModel, it!!)

            }
        }
    }
}