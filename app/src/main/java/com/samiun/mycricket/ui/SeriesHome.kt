package com.samiun.mycricket.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.LeaguesAdapter
import com.samiun.mycricket.databinding.FragmentSeriesBinding
import com.samiun.mycricket.network.overview.CricketViewModel

class SeriesHome : Fragment() {

    private lateinit var viewModel : CricketViewModel

    private lateinit var seriesRv: RecyclerView
    private var _binding:FragmentSeriesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSeriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        seriesRv = binding.browseleaguesrv
        viewModel.readLeagues.observe(viewLifecycleOwner){
            val adapterViewState = seriesRv.layoutManager?.onSaveInstanceState()
            seriesRv.layoutManager?.onRestoreInstanceState(adapterViewState)
            seriesRv.adapter = LeaguesAdapter(it!!)

        }

        binding.bottomNav?.let {
            it.menu.getItem(3).isChecked = true
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
                    findNavController().navigate(R.id.searchFragment)
                    return@setOnItemSelectedListener true
                }
                else ->{
                    return@setOnItemSelectedListener true
                }
            }
        }
    }


}