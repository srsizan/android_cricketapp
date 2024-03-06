package com.samiun.mycricket.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.samiun.mycricket.R
import com.samiun.mycricket.databinding.FragmentPlayerBinding
import com.samiun.mycricket.model.playerDetails.Career
import com.samiun.mycricket.network.overview.CricketViewModel
import com.samiun.mycricket.utils.Constants
import kotlinx.android.synthetic.main.fragment_match_infor.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayerFragment : Fragment() {
    private lateinit var viewModel: CricketViewModel

    private val navArgs by navArgs<PlayerFragmentArgs>()
    private var _binding: FragmentPlayerBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val PlayerId = navArgs.playerId


        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        viewModel.getPlayerCareer(PlayerId).observe(viewLifecycleOwner){
            if (it != null) {
                Log.e("Player Fragment ", "onViewCreated: ${it.image_path}", )
                Glide
                    .with(requireContext())
                    .load(it.image_path)
                    .placeholder(R.drawable.image_downloading)
                    .error(R.drawable.not_found_image)
                    .into(binding.playerImage)

                binding.playerName.text = it.fullname
                binding.playerAge.text = Constants.calculateAge(it.dateofbirth)
                binding.playerType.text= it.position?.name
               // binding.playerCountry.text =getCountry(it.country_id!!)
                GlobalScope.launch {
                    val country = it.country_id?.let { it1 -> viewModel.findCountryById(it1) }
                    withContext(Dispatchers.Main){
                        if (country != null) {
                            binding.playerCountry.text = country.name
                        }
                        else
                            binding.playerCountry.visibility= View.GONE

                    }
                }

                val careers: List<Career> = it.career!!
                var battingCareer = true
                showBattingStats(careers)
                binding.playerBatting.setOnClickListener {
                    showBattingStats(careers)
                }
                binding.playerBowling.setOnClickListener {
                    showBowlingStats(careers)
                }


            }
        }
    }
    private fun showBattingStats(careers: List<Career>) {

        binding.highestTV.text = "Highest"
        binding.averageTV.text = "Average"
        binding.economy.text = "100/50"

        val runs = getRuns(careers)
        binding.playert20runs.text = runs[0].toString()
        binding.playerOdruns.text = runs[1].toString()
        binding.playerTestruns.text = runs[2].toString()
        getMatches(careers)
        getInnings(careers)
        getBalls(careers)
        getHighest(careers)
        getAverage(careers)
        getStrikerate(careers)
        getBattingRecords(careers)

        binding.playert20runs.text = runs[0].toString()
        binding.playerOdruns.text = runs[1].toString()
        binding.playerTestruns.text = runs[2].toString()

        Log.e("Player Fragment", "Runs onViewCreated: ${runs[0]}, ${runs[1]}")
    }

    private fun showBowlingStats(careers: List<Career>) {
        binding.highestTV.text = "Wickets"
        binding.averageTV.text = "Average"
        binding.economy.text = "Economy"


        val runs = getBowlingRuns(careers)
        binding.playert20runs.text = runs[0].toString()
        binding.playerOdruns.text = runs[1].toString()
        binding.playerTestruns.text = runs[2].toString()
        getBowlingMatches(careers)
        getBowlingInnings(careers)
        getBowlingBalls(careers)
        getWickets(careers)
        getBowlingAverage(careers)
        getBowlingStrikerate(careers)
        getBowlingBest(careers)

    }

    private fun getBowlingBest(careers: List<Career>) {
        val runs = getBowlingRuns(careers)
        val balls = getBowlingBalls(careers)
        val t20 = 6* runs[0].toDouble()/balls[0].toDouble()
        val odi = 6* runs[1].toDouble()/balls[1].toDouble()
        val test = 6* runs[2].toDouble()/balls[2].toDouble()
        binding.playert20economy.text = "%.2f".format(t20)
        binding.playerOdieconomy.text = "%.2f".format(odi)
        binding.playerTestEconomy.text = "%.2f".format(test)

    }

    private fun getBowlingStrikerate(careers: List<Career>): List<Double> {
        val balls = getBowlingBalls(careers)
        val wickets = getWickets(careers)
        val sr  = mutableListOf<Double>()
        val t20sr = balls[0].toDouble()/wickets[0].toDouble()
        val odisr = balls[1].toDouble()/wickets[0].toDouble()
        val testsr = balls[2].toDouble()/wickets[2].toDouble()
        binding.playert20SR.text = "%.2f".format(t20sr)
        binding.playerTestSR.text = "%.2f".format(testsr)
        binding.playerOdSR.text ="%.2f".format(odisr)
        sr.add(t20sr)
        sr.add(odisr)
        sr.add(testsr)
        return sr
    }

    private fun getBowlingRuns(careers: List<Career>): List<Int> {
        val runs = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.bowling?.runs ?: 0 }
        runs.add(t20)



        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.bowling?.runs ?: 0 }
        runs.add(odi)

        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.bowling?.runs ?: 0 }
        runs.add(test)
        Log.e("Player Fragment", "Runs onViewCreated: ${runs[0]}, ${runs[1]}")

        return runs
    }

    private fun getBowlingAverage(careers: List<Career>): List<Double> {
        val runs = getRuns(careers)
        val wickets = getWickets(careers)
        val average  = mutableListOf<Double>()
        val t20average = runs[0].toDouble()/wickets[0].toDouble()
        val odiAverage = runs[1].toDouble()/wickets[0].toDouble()
        val testAverage = runs[2].toDouble()/runs[2].toDouble()
        binding.playert20average.text = "%.2f".format(t20average)
        binding.playerTestaverage.text = "%.2f".format(testAverage)
        binding.playerOdiaverage.text = "%.2f".format(odiAverage)
        average.add(t20average)
        average.add(odiAverage)
        average.add(testAverage)
        return average
    }

    private fun getWickets(careers: List<Career>): List<Int> {
        val wickets = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.bowling?.wickets ?: 0 }
        wickets.add(t20)


        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.bowling?.wickets ?: 0 }
        wickets.add(odi)


        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.bowling?.wickets ?: 0 }
        wickets.add(test)

        binding.playert20highest.text = t20.toString()
        binding.playerTesthighest.text = test.toString()
        binding.playerOdiHighest.text = odi.toString()

        return wickets

    }

    private fun getBowlingBalls(careers: List<Career>): List<Int> {
        val balls = mutableListOf<Int>()
        val listOver = mutableListOf<String>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { (it.bowling?.overs?.toInt()?.times(6))?.plus(((it.bowling?.overs?.rem(1))?.times(10))?.toInt()!!) ?: 0 }
        balls.add(t20)



        val odi= careers.filter { it.type =="ODI"}
            .sumOf { (it.bowling?.overs?.toInt()?.times(6))?.plus(((it.bowling?.overs?.rem(1))?.times(10))?.toInt()!!) ?: 0 }
        balls.add(odi)

        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { (it.bowling?.overs?.toInt()?.times(6))?.plus(((it.bowling?.overs?.rem(1))?.times(10))?.toInt()!!) ?: 0 }
        balls.add(test)

        binding.playert20balls.text = t20.toString()
        binding.playerTestballs.text = test.toString()
        binding.playerOdiballs.text = odi.toString()

        return balls
    }

    private fun getBowlingInnings(careers: List<Career>): List<Int> {

        val innings = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.bowling?.innings ?: 0 }
        innings.add(t20)

        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.bowling?.innings ?: 0 }
        innings.add(test)

        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.bowling?.innings ?: 0 }
        innings.add(odi)

        binding.playert20Innings.text = t20.toString()
        binding.playerTestInnings.text = test.toString()
        binding.playerOdInnigns.text = odi.toString()
        return innings
    }

    private fun getBowlingMatches(careers: List<Career>): List<Int> {
        val matches = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.bowling?.matches ?: 0 }
        matches.add(t20)

        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.bowling?.matches ?: 0 }
        matches.add(test)

        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.bowling?.matches ?: 0 }
        matches.add(odi)

        binding.playert20matches.text = t20.toString()
        binding.playerTestmathces.text = test.toString()
        binding.playerOdimatches.text = odi.toString()

        return matches
    }



    private fun getBattingRecords(careers: List<Career>) {
        val t20fifties= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.batting?.fifties ?: 0 }


        val odififites= careers.filter { it.type =="ODI"}
            .sumOf { it.batting?.fifties ?: 0 }


        val testfifties= careers.filter { it.type =="Test/5day"}
            .sumOf { it.batting?.fifties ?: 0 }

        val t20Hundreds= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.batting?.hundreds ?: 0 }


        val odiHundreds= careers.filter { it.type =="ODI"}
            .sumOf { it.batting?.hundreds ?: 0 }


        val testHundreds= careers.filter { it.type =="Test/5day"}
            .sumOf { it.batting?.hundreds ?: 0 }
        binding.playert20economy.text = "$t20Hundreds/$t20fifties"
        binding.playerOdieconomy.text = "$odiHundreds/$odififites"
        binding.playerTestEconomy.text = "$testHundreds/$testfifties"
    }


    private fun getStrikerate(careers: List<Career>): List<Double> {
        val strikerate = mutableListOf<Double>()
        val runs = getRuns(careers)
        val balls = getBalls(careers)
        val t20 =  (runs[0].toDouble()/balls[0].toDouble())*100
        val odi =  (runs[1].toDouble()/balls[1].toDouble())*100
        val test =  (runs[2].toDouble()/balls[2].toDouble())*100
        strikerate.add(t20)
        strikerate.add(odi)
        strikerate.add(test)
        Log.e("Strike Rate", "Strike Rate: $runs , $balls : $strikerate", )

        binding.playert20SR.text = "%.2f".format(t20)
        binding.playerTestSR.text ="%.2f".format(test)
        binding.playerOdSR.text = "%.2f".format(odi)
        return strikerate

    }

    private fun getAverage(careers: List<Career>): List<Double> {
        val runs = getRuns(careers)
        val inngings = getInnings(careers)
        val notOuts = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.batting?.not_outs ?: 0 }
        notOuts.add(t20)



        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.batting?.not_outs ?: 0 }
        notOuts.add(odi)

        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.batting?.not_outs ?: 0 }
        notOuts.add(test)


        val average = mutableListOf<Double>()
        Log.e("Average", "getAverage:$inngings: $notOuts: $runs ", )

        val t20average =runs[0].toDouble()/( inngings[0].toDouble()-notOuts[0].toDouble())
        val odiAverage =runs[1].toDouble()/( inngings[1].toDouble()-notOuts[1].toDouble())
        val testAverage =runs[2].toDouble()/( inngings[2].toDouble()-notOuts[2].toDouble())
        average.add(t20average)
        average.add(odiAverage)
        average.add(testAverage)

        binding.playert20average.text = "%.2f".format(t20average)
        binding.playerTestaverage.text = "%.2f".format(testAverage)
        binding.playerOdiaverage.text = "%.2f".format(odiAverage)

        return average


    }

    private fun getHighest(careers: List<Career>): MutableList<Int> {
        val highest = mutableListOf<Int>()

        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .maxOf { it.batting?.highest_inning_score ?: 0 }
        highest.add(t20)


        val odi= careers.filter { it.type =="ODI"}
            .maxOf { it.batting?.highest_inning_score ?: 0 }
        highest.add(odi)


        val test= careers.filter { it.type =="Test/5day"}
            .maxOf { it.batting?.highest_inning_score ?: 0 }
        highest.add(test)


        binding.playert20highest.text = t20.toString()
        binding.playerTesthighest.text = test.toString()
        binding.playerOdiHighest.text = odi.toString()

        return highest
    }

    private fun getBalls(careers: List<Career>): MutableList<Int> {
        val balls = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.batting?.balls_faced ?: 0 }
        balls.add(t20)



        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.batting?.balls_faced ?: 0 }
        balls.add(odi)

        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.batting?.balls_faced ?: 0 }
        balls.add(test)

        binding.playert20balls.text = t20.toString()
        binding.playerTestballs.text = test.toString()
        binding.playerOdiballs.text = odi.toString()

        return balls
    }

    private fun getInnings(careers: List<Career>): List<Int> {
        val innings = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.batting?.innings ?: 0 }
        innings.add(t20)

        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.batting?.innings ?: 0 }
        innings.add(odi)
        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.batting?.innings ?: 0 }
        innings.add(test)

        binding.playert20Innings.text = t20.toString()
        binding.playerTestInnings.text = test.toString()
        binding.playerOdInnigns.text = odi.toString()
        return innings
    }

    private fun getMatches(careers: List<Career>): List<Int> {
        val matches = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.batting?.matches ?: 0 }
        matches.add(t20)

        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.batting?.matches ?: 0 }
        matches.add(test)

        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.batting?.matches ?: 0 }
         matches.add(odi)

        binding.playert20matches.text = t20.toString()
        binding.playerTestmathces.text = test.toString()
        binding.playerOdimatches.text = odi.toString()

        return matches
    }

    fun getRuns(careers: List<Career>):List<Int>{

        val runs = mutableListOf<Int>()
        val t20= careers.filter { it.type =="T20"|| it.type =="T20I"}
            .sumOf { it.batting?.runs_scored ?: 0 }
        runs.add(t20)

        val odi= careers.filter { it.type =="ODI"}
            .sumOf { it.batting?.runs_scored ?: 0 }
        runs.add(odi)

        val test= careers.filter { it.type =="Test/5day"}
            .sumOf { it.batting?.runs_scored ?: 0 }
        runs.add(test)
        Log.e("Player Fragment", "Runs onViewCreated: ${runs[0]}, ${runs[1]}")

        return runs
    }
}