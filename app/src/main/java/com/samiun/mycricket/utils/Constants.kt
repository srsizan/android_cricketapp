package com.samiun.mycricket.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

class Constants {
    companion object{
        const val databaseName = "cricket_database"
        const val BASE_URL = "https://cricket.sportmonks.com/api/v2.0/"
        const val apikey = "Wy9K8UlUMHGRkfslTawlhRtVk3v47DIhh2VCgfPhfww0ox42CiJ5aECYEe7h"

        //API's

      //  hNf2oXFWaRWVINxXWzIZczPrbbH1db5WMoQ6osus2XhsK3Z5wI4D3Nsf8vTY

        const val api_token3 = "api_token=qEx13JbWRD8rtNU6dGUIpQIx6aHxawElzDxMs4EkGNpOMTrvqyjqZSHQdZIK"

        //const val api_token1 ="api_token=hNf2oXFWaRWVINxXWzIZczPrbbH1db5WMoQ6osus2XhsK3Z5wI4D3Nsf8vTY"
        const val api_token1 ="api_token=Wy9K8UlUMHGRkfslTawlhRtVk3v47DIhh2VCgfPhfww0ox42CiJ5aECYEe7h"

        const val api_token2 ="qEx13JbWRD8rtNU6dGUIpQIx6aHxawElzDxMs4EkGNpOMTrvqyjqZSHQdZIK"
        const val api_token ="0ElKvYYdKqBDgRJc367869n3iPEljCurdPpqnjIMFcj3HqqHvAL35XJGXios"
        //const val api_token ="hNf2oXFWaRWVINxXWzIZczPrbbH1db5WMoQ6osus2XhsK3Z5wI4D3Nsf8vTY"
        const val COUNTRY_END_POINT = "countries"
        const val LEAGUES_END_POINT ="leagues?$api_token1"

        const val TEAM_END_POINT ="teams?$api_token1"
        const val UPCOMING_END_POINT ="fixtures?filter[starts_between]=2023-02-26T00:00:00.000000Z,2023-03-10T00:00:00.000000Z&$api_token1"

        // https://cricket.sportmonks.com/api/v2.0/fixtures/:FIXTURE_ID?include=runs&api_token=FMRNjV3cC2q6xE31ya2oXBTizo4H1AMoYDXtPWszp62FBn6FMz7UAWXvaWWd
        fun getTime(days:Int):String{
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE,days)
            val time = calendar.time
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            return dateFormat.format(time)
        }

        fun dateFormat(inputDate: String): String{
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
            val outputFormat = SimpleDateFormat("EEE, MMM d")

            val date = inputFormat.parse(inputDate)
            val outputDate = outputFormat.format(date)

            return outputDate
        }
        fun timeFormat(inputDate: String): String{

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")
            val outputFormat = SimpleDateFormat("hh:mm a")

            val date = inputFormat.parse(inputDate)
            val time = outputFormat.format(date)

            return time
        }
        fun upcomingtimeFormat(inputDate: String): String{
            val outputFormat = DateTimeFormatter.ofPattern("hh:mm a")

            val instant = Instant.parse(inputDate)
            val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            val time = outputFormat.format(date)

            return time
        }

        fun calculateAge(dateString: String?): String {
            try {
                val dob = LocalDate.parse(dateString)
                val today = LocalDate.now()
                return Period.between(dob, today).years.toString()+"Years"
            }
            catch (e:Exception){
                Log.e("Constant Calculate Age Exception", "calculateAge: $e", )
                return "not available"
            }
        }

        fun homedata(results: List<com.samiun.mycricket.model.teamDetails.Result>?, id: Int): List<Any> {
            val info = mutableListOf<Any>()
            var total = 0
            var home = 0
            var away = 0
            var percentage = 0.0

            if(results!=null){
                for(result in results){
                    if(result in results){
                        if(result.localteam_id ==id){
                            total++
                            if(result.winner_team_id ==id){
                                home++
                            }
                            else{
                                away++
                            }
                        }
                    }
                }
            }
            percentage =( home.toDouble()/total.toDouble())*100
            info.add(total)
            info.add(home)
            info.add(away)
            info.add(percentage)
            return info
        }



        fun awaydata(results: List<com.samiun.mycricket.model.teamDetails.Result>?, id: Int): List<Any> {
            val info = mutableListOf<Any>()
            var total = 0
            var home = 0
            var away = 0
            var percentage = 0.0

            if(results!=null){
                for(result in results){
                    if(result in results){
                        if(result.localteam_id !=id){
                            total++
                            if(result.winner_team_id ==id){
                                home++
                            }
                            else{
                                away++
                            }
                        }
                    }
                }
            }
            percentage =( home.toDouble()/total.toDouble())*100
            info.add(total)
            info.add(home)
            info.add(away)
            info.add(percentage)
            return info
        }
        fun totalTeamWon(results: List<com.samiun.mycricket.model.teamDetails.Result>?, id: Int?): Int {
            var won = 0
            if (results != null) {
                for (result in results) {
                    if (result.winner_team_id == id) {
                        won++
                    }
                }
            }
            return won
        }

        fun totalOtherWon(results: List<com.samiun.mycricket.model.teamDetails.Result>?, id: Int?): Int {
            var won = 0
            if (results != null) {
                for (result in results) {
                    if (result.winner_team_id != id && result.winner_team_id!=null) {
                        won++
                    }
                }
            }
            return won
        }
    }

}