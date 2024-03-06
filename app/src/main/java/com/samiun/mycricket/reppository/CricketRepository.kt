package com.samiun.mycricket.reppository

import androidx.lifecycle.LiveData
import com.samiun.mycricket.database.CricketDao
import com.samiun.mycricket.model.country.Data
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRunEntity
import com.samiun.mycricket.model.league.Leagues
import com.samiun.mycricket.model.officials.OfficialEntity
import com.samiun.mycricket.model.player.PlayerEntity
import com.samiun.mycricket.model.players.PlayerData
import com.samiun.mycricket.model.ranking.RankingData
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.model.venue.VenueEntity

class CricketRepository(private val cricketDao: CricketDao) {
    val readFixtureEntity:LiveData<List<FixtureEntity>> = cricketDao.readAllFixtures()
    suspend fun addFixtures(fixture: List<FixtureEntity>){
        cricketDao.addFixture(fixture)
    }

    val readFixtureWithRunEntity: LiveData<List<FixtureWithRunEntity>> = cricketDao.readFixtureWithRun()
    val readTeamEntity: LiveData<List<TeamEntity>> = cricketDao.readTeamEntity()
    val readLeagues: LiveData<List<Leagues>> = cricketDao.readLeagues()

    val readPlayerData: LiveData<List<PlayerData>> = cricketDao.readPlayerData()

    suspend fun addFixturesWithRun(fixtureWithRunEntity:List<FixtureWithRunEntity>){
        cricketDao.addFixtureWithRun(fixtureWithRunEntity)
    }

    suspend fun addLeague(leagueList: List<Leagues>){
        cricketDao.addleague(leagueList)
    }
    suspend fun addCountry(countryList: List<Data>){
        cricketDao.addCountry(countryList)
    }



    suspend fun addTeams(teamEntity: List<TeamEntity>){
        cricketDao.addTeam(teamEntity)
    }

    fun readTeam(id: Int): TeamEntity{
        return cricketDao.readTeamId(id)
    }

    fun readFixturewithRun(id: Int): FixtureWithRunEntity{
        return cricketDao.readFixturewitnrun(id)
    }

    fun readFixturewithLeagues(id: Int): LiveData<List<FixtureWithRunEntity>>{
        return cricketDao.readFixtureByLeague(id)
    }

    fun readFixtureUpcomingbyleague(id: Int): LiveData<List<FixtureEntity>>{
        return cricketDao.readUpcomingFixturebyleage(id)
    }


    suspend fun readOfficial(id: Int): OfficialEntity{
        return cricketDao.readOfficialId(id)
    }

    suspend fun readLeague(id: Int): Leagues{
        return cricketDao.readLeagues(id)
    }

    suspend fun readPlayer(id: Int): PlayerEntity{
        return cricketDao.readPlayer(id)
    }
    suspend fun readVenue(id: Int): VenueEntity{
        return cricketDao.readVenueId(id)
    }
    suspend fun readCountry(id: Int): Data{
        return cricketDao.readCountryId(id)
    }


    suspend fun addRanking(rankList: List<RankingData>){
        cricketDao.addRanking(rankList)
    }

    suspend fun addVenue(venueEntity: List<VenueEntity>){
        cricketDao.addVenues(venueEntity)
    }

    suspend fun addOfficials(official: List<OfficialEntity>){
        cricketDao.addOfficials(official)
    }

    suspend fun addPlayers(player: List<PlayerData>){
        cricketDao.addPlayer(player)
    }

    suspend fun getRanking(gender:String, format:String): RankingData{
        return cricketDao.getRanking(gender,format)
    }


}