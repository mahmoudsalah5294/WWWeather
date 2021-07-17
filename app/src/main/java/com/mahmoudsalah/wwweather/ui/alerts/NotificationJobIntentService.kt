package com.mahmoudsalah.wwweather.ui.alerts

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mahmoudsalah.wwweather.MainActivity
import com.mahmoudsalah.wwweather.R

class NotificationJobIntentService:JobIntentService() {
    override fun onHandleWork(intent: Intent) {



        createNotificationChannel()


    }

    companion object {
        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, NotificationJobIntentService::class.java, 0, work)
        }

        val channel_id = "wwweather"
    }




    private fun createNotificationChannel()
    {
        val notificationMa = NotificationManagerCompat.from(this)
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 1, notificationIntent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.app_name)
            val description = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channel_id, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)


// notificationId is a unique int for each notification that you must define
            notificationMa.notify(2, NotificationCompat.Builder(this, channel_id).setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("WWWeather")
                    .setContentText("TheWeather fine")
//                    .setStyle(NotificationCompat.BigTextStyle()
//                            .bigText(myTrip.getStartpoint().toString() + " -> " + myTrip.getEndpoint()))
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .setContentIntent(pIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT).build())
        } else {

            notificationMa.notify(2, NotificationCompat.Builder(this, channel_id).setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("WWWeather")
                    .setContentText("TheWeather fine")
//                    .setStyle(NotificationCompat.BigTextStyle()
//                            .bigText(myTrip.getStartpoint().toString() + " -> " + myTrip.getEndpoint()))
                    .setAutoCancel(true)
                    .setOngoing(true)
                    .setContentIntent(pIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT).build())
        }
    }
}