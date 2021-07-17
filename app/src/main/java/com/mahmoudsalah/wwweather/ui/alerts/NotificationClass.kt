package com.mahmoudsalah.wwweather.ui.alerts

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class NotificationClass:Application() {
    companion object{
        val channel = "channel"
    }

    override fun onCreate() {
        super.onCreate()
        notificationChannel()
    }
     fun notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channel, "Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "This is Notification Channel"
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}