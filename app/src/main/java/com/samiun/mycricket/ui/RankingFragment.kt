package com.samiun.mycricket.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.RankingAdapter
import com.samiun.mycricket.databinding.FragmentRankingBinding
import com.samiun.mycricket.network.overview.CricketViewModel
import kotlinx.android.synthetic.main.match_list.*


class RankingFragment : Fragment() {
    private lateinit var viewModel: CricketViewModel
    private lateinit var rankingRecyclerView: RecyclerView


    private var _binding: FragmentRankingBinding? = null
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

        _binding = FragmentRankingBinding.inflate(inflater)
        return binding.root
    }
    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]
        var gender = "men"
        var format = "T20I"
        rankingAdapter(gender,format)

        binding.genderMan.setOnClickListener {
            binding.genderMan.isChecked = true

            gender = "men"

            ///binding.genderMan.setBackgroundColor(R.color.colorOnPrimary)
            binding.testranking.visibility = View.VISIBLE
            rankingAdapter(gender,format)
        }
        binding.genderWoman.setOnClickListener {
            binding.genderWoman.isChecked = true

            gender = "women"
            binding.testranking.visibility = View.GONE
            rankingAdapter(gender,format)

        }
        binding.testranking.setOnClickListener {
            binding.testranking.isChecked = true

            format = "TEST"
            rankingAdapter(gender,format)

        }
        binding.t20ranking.setOnClickListener {
            binding.t20ranking.isChecked = true
            format = "T20I"
            rankingAdapter(gender,format)
        }
        binding.odiranking.setOnClickListener {
            binding.odiranking.isChecked = true
            format = "ODI"
            rankingAdapter(gender,format)
        }

        val blueColor: Int = ContextCompat.getColor(requireContext(), R.color.teal_200)
        val whiteColor: Int = ContextCompat.getColor(requireContext(), R.color.colorOnPrimary)
//        binding.t20ranking.setBackgroundColor(R.color.teal_200)
//        binding.genderMan.setBackgroundColor(R.color.teal_200)
        binding.t20ranking.setBackgroundColor(blueColor)
        binding.genderMan.setBackgroundColor(blueColor)
        binding.t20ranking.isChecked = true
        binding.genderMan.isChecked = true
        binding.formatGroup.addOnButtonCheckedListener{_, checkedId, isChecked ->
            when (checkedId) {
                R.id.t20ranking -> binding.t20ranking.setBackgroundColor(if (isChecked) blueColor else whiteColor)
                R.id.odiranking -> binding.odiranking.setBackgroundColor(if (isChecked) blueColor else whiteColor)
                R.id.testranking -> binding.testranking.setBackgroundColor(if (isChecked) blueColor else whiteColor)
            }
        }


        binding.genderGroup.addOnButtonCheckedListener{_, checkedId, isChecked ->
            when (checkedId) {
                R.id.gender_man -> binding.genderMan.setBackgroundColor(if (isChecked) blueColor else whiteColor)
                R.id.gender_woman -> binding.genderWoman.setBackgroundColor(if (isChecked) blueColor else whiteColor)
            }
        }

        binding.bottomNav?.let {
            it.menu.getItem(1).isChecked = true
        }

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.rankingFragment->{
                    return@setOnItemSelectedListener true
                }
                R.id.homeFragment->{
                    findNavController().navigate(R.id.homeFragment)
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

        rankingRecyclerView = binding.rankingRv
        rankingAdapter(gender,format)


        }

    fun rankingAdapter(gender:String, format: String){
        try {
            viewModel.getRanking(gender, format).observe(viewLifecycleOwner){
                if(it!=null){
                    rankingRecyclerView.adapter = RankingAdapter(it.team!!)
                }
            }
        }catch (e:Exception){
            Log.e("Ranking Fragment Exception", "rankingAdapter:$e ", )
        }

    }

}