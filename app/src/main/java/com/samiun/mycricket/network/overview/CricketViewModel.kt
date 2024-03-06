package com.samiun.mycricket.network.overview
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.samiun.mycricket.database.CricketDatabase
import com.samiun.mycricket.reppository.CricketRepository
import com.samiun.mycricket.model.country.Data
import com.samiun.mycricket.model.fixture.Fixture
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.fixturewithdetails.FixtureWithDetailsData
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRun
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRunEntity
import com.samiun.mycricket.model.league.Leagues
import com.samiun.mycricket.model.liveScore.LiveScoreData
import com.samiun.mycricket.model.officials.OfficialEntity
import com.samiun.mycricket.model.player.PlayerEntity
import com.samiun.mycricket.model.playerDetails.PlayerDetailsData
import com.samiun.mycricket.model.players.PlayerData
import com.samiun.mycricket.model.ranking.RankingData
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.model.teamDetails.TeamDetailsData
import com.samiun.mycricket.model.teamsquad.TeamSquadData
import com.samiun.mycricket.model.venue.VenueEntity
import com.samiun.mycricket.network.CricketApi
import com.samiun.mycricket.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CricketViewModel(application: Application): AndroidViewModel(application){

    //For API
    var viewpargerpage =0
    private val _countries = MutableLiveData<List<Data>>()
    private val countries: LiveData<List<Data>> = _countries
    private val _leagues = MutableLiveData<List<Leagues>>()
    private val leagues: LiveData<List<Leagues>> get() = _leagues
    private val _ranking = MutableLiveData<List<RankingData>>()
    private val ranking: LiveData<List<RankingData>> get() = _ranking

    private val _venues = MutableLiveData<List<VenueEntity>>()
    private val venues: LiveData<List<VenueEntity>> get() = _venues

    private val _officials = MutableLiveData<List<OfficialEntity>>()
    private val official: LiveData<List<OfficialEntity>> get() = _officials

    private val _player = MutableLiveData<List<PlayerData>>()
    private val player: LiveData<List<PlayerData>> get() = _player
    private val _fixture = MutableLiveData<List<FixtureEntity>>()
    private val fixture: LiveData<List<FixtureEntity>> get() = _fixture
    private val _fixturewithrun = MutableLiveData<List<FixtureWithRunEntity>>()
    private val fixturewithrun: LiveData<List<FixtureWithRunEntity>> get()  = _fixturewithrun
    private val _fixturewithDetails = MutableLiveData<FixtureWithDetailsData?>()
    val fixturewithDetails: LiveData<FixtureWithDetailsData?> get()  = _fixturewithDetails

    private val _liveScore = MutableLiveData<List<LiveScoreData>>()
    private val liveScore: LiveData<List<LiveScoreData>> get()  = _liveScore

    private val _teamDetails = MutableLiveData<TeamDetailsData?>()
    private val teamDetails: LiveData<TeamDetailsData?> get()  = _teamDetails

    private val _teamSquad = MutableLiveData<TeamSquadData>()
    private val teamSquad: LiveData<TeamSquadData> get()  = _teamSquad

    private val _playerData = MutableLiveData<PlayerDetailsData>()
    private val playerData: LiveData<PlayerDetailsData> get()  = _playerData



    private val _team = MutableLiveData<List<TeamEntity>>()
    private val team: LiveData<List<TeamEntity>> = _team



    private val repository: CricketRepository

    val readFixtureEntity :LiveData<List<FixtureEntity>>
    val readFixtureWithRunEntity: LiveData<List<FixtureWithRunEntity>>
    val readTeamEntity: LiveData<List<TeamEntity>>
    val readPlayerData: LiveData<List<PlayerData>>
    val readLeagues: LiveData<List<Leagues>>


    init{
        val cricketDao = CricketDatabase.getDatabase(application).cricketDao()
        repository = CricketRepository(cricketDao)
        readFixtureEntity = repository.readFixtureEntity
        readFixtureWithRunEntity = repository.readFixtureWithRunEntity
        readTeamEntity = repository.readTeamEntity
        readLeagues = repository.readLeagues

        readPlayerData = repository.readPlayerData
        //readTeam = repository.readTeam(id)
    }

    fun getCountries(){
        viewModelScope.launch {
            try {
               // Log.d("Overview Fragment", "getCountries: ")
                _countries.value = CricketApi.retrofitService.getCountries(Constants.apikey).data
                countries.value?.let { Log.d("Api", "getCountries: ${it.get(0).name}") }

                countries.value?.let { addCountryList(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.e("Cricket View Model Countries","$e")
            }
        }
    }

    fun findTeamById(id: Int): TeamEntity{
        return repository.readTeam(id)
    }


    fun findFixturebyid(id: Int): FixtureWithRunEntity{
        return repository.readFixturewithRun(id)
    }

    fun findFixuteByLeague(id: Int): LiveData<List<FixtureWithRunEntity>>{
        return repository.readFixturewithLeagues(id)
    }

    fun findUpcomingbyleage(id: Int): LiveData<List<FixtureEntity>>{
        return repository.readFixtureUpcomingbyleague(id)
    }



    suspend fun findOfficialbyId(id: Int): OfficialEntity{
        viewModelScope.launch(Dispatchers.IO) {  }
        return repository.readOfficial(id)
    }

    suspend fun findLeaguebyId(id: Int): Leagues{
        viewModelScope.launch(Dispatchers.IO) {  }
        return repository.readLeague(id)
    }

    suspend fun findPlayerbyId(id: Int): PlayerEntity{
        viewModelScope.launch(Dispatchers.IO) {  }
        return repository.readPlayer(id)
    }


    suspend fun findVenueById(id: Int): VenueEntity{
        viewModelScope.launch(Dispatchers.IO) {  }
        return repository.readVenue(id)
    }

     suspend fun findCountryById(id: Int): Data {
        viewModelScope.launch(Dispatchers.IO) {  }
        return repository.readCountry(id)
    }



    private fun addCountryList(countryList: List<Data>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCountry(countryList)
        }
    }

    fun getLeagues(){
        viewModelScope.launch {
            try {
                // Log.d("Overview Fragment", "getCountries: ")
                _leagues.value = CricketApi.retrofitService.getLeagues().data
                leagues.value?.let { Log.d("Api", "getCountries: ${it.get(0).name}") }

                leagues.value?.let { addLeagueList(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.e("Cricket View Model Leagues","$e")
            }
        }
    }

    private fun addLeagueList(leagues: List<Leagues>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLeague(leagues)
        }
    }

    fun getRanking(){
        viewModelScope.launch {
            try {
                // Log.d("Overview Fragment", "getCountries: ")
                _ranking.value = CricketApi.retrofitService.getRanking().data
                ranking.value?.let { Log.d("Api", "getCountries: ${it.get(0).team}") }

                ranking.value?.let { addRanking(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.e("Cricket View Model Ranking","$e")
            }
        }
    }
    private fun addRanking(ranking: List<RankingData>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRanking(ranking)
        }
    }

    fun getVenus(){
        viewModelScope.launch {
            try {
                _venues.value = CricketApi.retrofitService.getVenus().data
                venues.value?.let { addVenues(it) }
            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.e("Cricket View Model Venues","$e")
            }
        }
    }
    private fun addVenues(venues: List<VenueEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addVenue(venues)
        }
    }


    fun getOfficials(){
        viewModelScope.launch {
            try {
                _officials.value = CricketApi.retrofitService.getOfficials().data
                official.value?.let { addOfficilas(it) }
            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.e("Cricket View Model Officials","$e")
            }
        }
    }

    private fun addOfficilas(official: List<OfficialEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOfficials(official)
        }
    }



    fun getPlayers(){
        viewModelScope.launch {
            try {
                _player.value = CricketApi.retrofitService.getPlayers().data
                player.value?.let { addPlayer(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.e("Cricket View Model Players","$e")
            }
        }
    }
    private fun addPlayer(players: List<PlayerData>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPlayers(players)
        }
    }

    fun getFixtures(){
        val startDate = Constants.getTime(0)//"2023-02-26T00:00:00.000000Z"
        val endDate = Constants.getTime(35)//"2023-04-10T00:00:00.000000Z"
        viewModelScope.launch {
            try {
                Log.d("Overview Fragment", "Fixtue: ")
                _fixture.value = CricketApi.retrofitService.getFixtures("$startDate,$endDate").data

                //_fixture.value = CricketApi.retrofitService.getFixture(startDate,endDate).data
                //fixture.value?.let { Log.d("Api", "Fixture: ${it.get(0).note}") }

                fixture.value?.let { addFixtureList(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.e("Cricket View Model Fixture","$e")
            }
        }
    }

    private fun addFixtureList(fixture: List<FixtureEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFixtures(fixture)
            Log.d("FIXTURE", "FIXTURE: ${fixture.get(0).elected}")
        }
    }

    private fun addFixtureWithRun(fixtureWithRunEntity: List<FixtureWithRunEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFixturesWithRun(fixtureWithRunEntity)
            Log.d("FIXTURE With Run", "FIXTURE With Run: ${fixtureWithRunEntity.get(0)}")
        }
    }

    fun getTeams(){
        viewModelScope.launch {
            try {
                Log.d("Overview Fragment", "Teams: ")
                _team.value = CricketApi.retrofitService.getTeams().data
                team.value?.let { Log.d("Api", "Team: ${it.get(0).name}") }

                team.value?.let { addTeam(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.e("Cricket View Model Teams","$e")
            }
        }
    }

    private fun addTeam(teams: List<TeamEntity>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTeams(teams)
        }
    }



    fun getDetailsByMatch(fixtureID: Int): LiveData<FixtureWithDetailsData?> {

        viewModelScope.launch {
            try {
                Log.d("Fixture with Run", "Fixtuer: $fixtureID")
                _fixturewithDetails.value = CricketApi.retrofitService.getMatchDetails(fixtureID).data
                fixturewithDetails.value?.let {
                }
                Log.e("get details Api", "${fixturewithrun.value?.get(0)?.runs?.get(0)?.score}")

            } catch (e: java.lang.Exception) {
                _fixturewithrun.value = listOf()
                Log.e("Cricket View Model Details By Match","$e")
            }
        }

        return fixturewithDetails

    }


    fun getLiveMatch(): LiveData<List<LiveScoreData>> {

        viewModelScope.launch {
            try {
                Log.d("Live with Run", "Fixtuer: ")
                _liveScore.value = CricketApi.retrofitService.getLiveMatches().data
                _liveScore.value?.let {
                }

            } catch (e: java.lang.Exception) {
                Log.e("Live Score By Match","$e")
            }
        }

        return liveScore

    }

/*    fun getRuns(id:Int): LiveData<List<FixtureWithRunEntity>> {

        viewModelScope.launch {
            try {
                Log.d("Runs By match", "Fixtuer: ")
                _fixturewithrun.value = CricketApi.retrofitService.getRuns(id).data
                _fixturewithrun.value?.let {
                }
                Log.e("get details Api", "${liveScore.value?.get(0)?.runs?.get(0)?.score}")

            } catch (e: java.lang.Exception) {
                Log.e("Cricket View Model Live Score By Match","$e")
            }
        }
        return fixturewithrun
    }*/


    fun getTeamDetails(id: Int): LiveData<TeamDetailsData?> {

        viewModelScope.launch {
            try {
               // _fixturewithDetails.value = CricketApi.retrofitService.getMatchDetails(fixtureID).data
                Log.e("Team API", "getTeamDetails: $id" )
                _teamDetails.value = CricketApi.retrofitService.getTeamDetails(id).data

                _teamDetails.value?.let {
                }
                Log.e("get Team Api", "${teamDetails.value!!.name}")

            } catch (e: java.lang.Exception) {
                _fixturewithrun.value = listOf()
                Log.e("Cricket View Model Get Team Details","$e")
            }
        }

        return teamDetails

    }


    fun getTeamSquad(id: Int): LiveData<TeamSquadData> {
        viewModelScope.launch {
            try {
                // _fixturewithDetails.value = CricketApi.retrofitService.getMatchDetails(fixtureID).data
                Log.e("Team API", "getTeamDetails: $id" )
                _teamSquad.value = CricketApi.retrofitService.getTeamSquad(id).data

                _teamSquad.value?.let {
                }
                delay(1000)

            } catch (e: java.lang.Exception) {
                _fixturewithrun.value = listOf()
                Log.e("Cricket View Model Get Team Squad","$e")
            }
        }
        return teamSquad
    }

    fun getPlayerCareer(id: Int): LiveData<PlayerDetailsData> {

        viewModelScope.launch {
            try {
                // _fixturewithDetails.value = CricketApi.retrofitService.getMatchDetails(fixtureID).data
                Log.e("Team API", "getTeamDetails: $id" )
                _playerData.value = CricketApi.retrofitService.getPlayerDetails(id).data

                _playerData.value?.let {
                }
            } catch (e: java.lang.Exception) {
                _fixturewithrun.value = listOf()
                Log.e("Cricket View Model Get Team Squad","$e")
            }
        }
        return playerData
    }

    fun getFixturesWithRun(){
        val startDate = Constants.getTime(0)//"2023-02-26T00:00:00.000000Z"
        val endDate = Constants.getTime(-30)//"2023-04-10T00:00:00.000000Z"
        viewModelScope.launch {
            try {
                Log.d("Overview Fragment with runs", "Fixtue: ")
                _fixturewithrun.value = CricketApi.retrofitService.getFixtureWithRun().data

                fixturewithrun.value?.let { addFixtureWithRun(it) }

            }
            catch (e: java.lang.Exception) {
                _countries.value = listOf()
                Log.e("Cricket View Model Countries","$e")
            }
        }
    }


    fun getRanking(gender:String, format:String): LiveData<RankingData> {
        val result = MutableLiveData<RankingData>()
        viewModelScope.launch(Dispatchers.IO) {
           val ranking = repository.getRanking(gender, format)
            result.postValue(ranking)
        }
        return result
    }

}