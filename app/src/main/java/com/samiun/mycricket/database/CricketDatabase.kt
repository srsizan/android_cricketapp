package com.samiun.mycricket.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.samiun.mycricket.model.country.Data
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.model.fixturewithrun.FixtureWithRunEntity
import com.samiun.mycricket.model.league.Leagues
import com.samiun.mycricket.model.officials.OfficialEntity
import com.samiun.mycricket.model.players.PlayerData
import com.samiun.mycricket.model.ranking.RankingData
import com.samiun.mycricket.model.team.TeamEntity
import com.samiun.mycricket.model.venue.VenueEntity
import com.samiun.mycricket.utils.AnyTypeConverter
import com.samiun.mycricket.utils.Constants
import com.samiun.mycricket.utils.RunsConverter
import com.samiun.mycricket.utils.TeamsConverter

@Database(entities = [Data::class, Leagues::class,TeamEntity::class, FixtureEntity::class, FixtureWithRunEntity::class, RankingData::class, PlayerData::class,VenueEntity::class, OfficialEntity::class], version =17, exportSchema = false)
@TypeConverters(RunsConverter::class, AnyTypeConverter::class, TeamsConverter::class)
abstract class CricketDatabase: RoomDatabase() {
    abstract fun cricketDao(): CricketDao

    companion object{
        @Volatile
        private var INSTANCE: CricketDatabase? = null

        fun getDatabase(context: Context): CricketDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CricketDatabase::class.java,
                    Constants.databaseName
                )
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}