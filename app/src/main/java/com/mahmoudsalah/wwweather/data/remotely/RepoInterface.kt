package com.mahmoudsalah.wwweather.data.remotely

import androidx.lifecycle.LiveData
import com.mahmoudsalah.wwweather.model.Alerts
import com.mahmoudsalah.wwweather.model.WeatherModel

interface RepoInterface {

    suspend fun getCurrentWeather(latitude: Double, longitude: Double): LiveData<WeatherModel>

    suspend fun getAlertWeather(latitude: Double, longitude: Double): LiveData<Alerts>
}