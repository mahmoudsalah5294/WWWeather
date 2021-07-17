package com.mahmoudsalah.wwweather.ui.alerts

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.mahmoudsalah.wwweather.model.Alert
import com.mahmoudsalah.wwweather.model.AlertModelConverter
import com.mahmoudsalah.wwweather.util.MyApplication
import java.util.*

class Alarm(val context:Context ,val alert:Alert) {
    lateinit var timelist: List<String>
    lateinit var datelist: List<String>
    lateinit var pendingIntent: PendingIntent
    @RequiresApi(Build.VERSION_CODES.N)
    fun setAlarm(){
        formatDate()
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR,datelist[2].toInt())
        cal.set(Calendar.MONTH,datelist[1].toInt()-1)
        cal.set(Calendar.DAY_OF_MONTH,datelist[0].toInt())
        cal.set(Calendar.HOUR_OF_DAY,timelist[0].toInt())
        cal.set(Calendar.MINUTE,timelist[1].toInt())
        cal.set(Calendar.SECOND,0)

        val converter = AlertModelConverter()

        val alertString = converter.toString(alert)

        val intent = Intent(context,MyBrodCastReciver::class.java)

        intent.putExtra("alertModel",alertString)
        intent.putExtra("type","notification")

        pendingIntent = PendingIntent.getBroadcast(context,alert.id,intent,0)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.timeInMillis,pendingIntent)

    }
    fun formatDate(){


        timelist = alert.fromTime.split(":")

        datelist = alert.fromDate.split("/")

    }
    fun cancelAlert(){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,MyBrodCastReciver::class.java)
        pendingIntent = PendingIntent.getBroadcast(context,alert.id,intent,0)
        alarmManager.cancel(pendingIntent)
    }
}