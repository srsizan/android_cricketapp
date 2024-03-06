package com.samiun.mycricket.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.samiun.mycricket.R
import com.samiun.mycricket.ui.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCricketWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            val fragment = HomeFragment()

            fragment.getArticlesBackgroud()
            Log.d("Worker Fragment", "doWork: ")
        }

        showNotifiaclitons()
        return Result.success()
    }


    private fun showNotifiaclitons() {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        val channelId = "channel-01"
        val channelName = "Bowled Cricket Update"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Bowled Updates")
            .setContentText("New matches have been added to the app.")
            .setAutoCancel(true)

        notificationManager.notify(notificationId, notificationBuilder.build())


    }
}
