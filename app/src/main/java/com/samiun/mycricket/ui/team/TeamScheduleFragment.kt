package com.samiun.mycricket.ui.team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.samiun.mycricket.R
import com.samiun.mycricket.adapter.UpcomingMatchAdapter
import com.samiun.mycricket.databinding.FragmentMatchInforBinding
import com.samiun.mycricket.databinding.FragmentTeamScheduleBinding
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.fixture.LocalteamDlData
import com.samiun.mycricket.model.fixture.VisitorteamDlData
import com.samiun.mycricket.model.teamDetails.Fixture
import com.samiun.mycricket.network.overview.CricketViewModel


class TeamScheduleFragment : Fragment() {

    private lateinit var viewModel: CricketViewModel
    val gerArgs: TeamScheduleFragmentArgs by navArgs()
    private var _binding: FragmentTeamScheduleBinding? = null
    private lateinit var teamscheduleRecylerview: RecyclerView

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeamScheduleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = gerArgs.teamdetails
        viewModel = ViewModelProvider(this)[CricketViewModel::class.java]

        var fixtureEntity: List<FixtureEntity>
        val fixtures = data.fixtures?.let { fixtureToFixtureEntity(it) }


        teamscheduleRecylerview = binding.teamFixtureRv
        val adapterViewState = teamscheduleRecylerview.layoutManager?.onSaveInstanceState()
        teamscheduleRecylerview.layoutManager?.onRestoreInstanceState(adapterViewState)
        teamscheduleRecylerview.adapter =
            fixtures?.let { UpcomingMatchAdapter(requireContext(), viewModel, it) }

    }
    fun fixtureToFixtureEntity(fixtures : List<Fixture>): List<FixtureEntity>{
        return  fixtures.map { fixture ->
            FixtureEntity(
                null,
                null,
                null,
                fixture.follow_on,

            fixture.id,

            fixture.last_period,
            fixture.league_id,
            fixture.live,
            null,
            fixture.localteam_id,
            null,
            null,
            fixture.note,
            null,
            fixture.resource,
            fixture.round,
            fixture.rpc_overs.toString(),
            fixture.rpc_target.toString(),
            fixture.season_id,
            null,
            fixture.stage_id,
            fixture.starting_at,
            fixture.status,
            fixture.super_over,
            null,
            null,
            null,
            fixture.type,
            null,
            null,
            fixture.visitorteam_id,
            fixture.weather_report,
null
            )
        }
    }

}