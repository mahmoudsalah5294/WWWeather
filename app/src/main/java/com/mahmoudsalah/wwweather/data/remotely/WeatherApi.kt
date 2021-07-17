package com.mahmoudsalah.wwweather

import com.mahmoudsalah.wwweather.model.Alerts
import com.mahmoudsalah.wwweather.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface WeatherApi {

@GET
suspend fun getWeather(@Url url: String):Response<WeatherModel>

@GET
suspend fun getAlerts(@Url url:String):Response<Alerts>

}
