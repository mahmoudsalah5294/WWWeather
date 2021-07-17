package com.mahmoudsalah.wwweather.ui.display_alert

import android.content.Context
import com.mahmoudsalah.wwweather.WeatherService
import com.mahmoudsalah.wwweather.data.local.AlertsDatabase
import com.mahmoudsalah.wwweather.model.Alert
import com.mahmoudsalah.wwweather.model.Alerts
import com.mahmoudsalah.wwweather.model.WeatherModel
import com.mahmoudsalah.wwweather.ui.alerts.AlertRepo
import com.mahmoudsalah.wwweather.util.MyApplication
import retrofit2.Response

class ShowAlertRepo() {

    suspend fun deleteFromDatabase(alert: Alert){
        val alertsDao = AlertsDatabase.getAlertData(MyApplication.getContext()).alertDao()
        val alertRepo = AlertRepo(alertsDao)
        alertRepo.deleteAlert(alert)
    }

    suspend fun getURIConnect(lat:String,lon:String,unit:String,lang:String): Response<Alerts> {
        val url = "onecall?lat=$lat&lon=$lon&units=$unit&lang=$lang&exclude=current,minutely,hourly,daily&appid=ef414347b1c0f0e2222a4525726ca01d"

        val service = WeatherService.getWeatherService().getAlerts(url)

        return service
    }


//    fun getSettings(): MutableList<String> {
//        val unit = settingsSharedPreferences.getString("unit","metric")
//        val language = settingsSharedPreferences.getString("language","en")
//        return mutableListOf(unit?:"metric",language?:"en")
//    }
}