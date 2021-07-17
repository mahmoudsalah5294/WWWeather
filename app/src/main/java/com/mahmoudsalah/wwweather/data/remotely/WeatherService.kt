package com.mahmoudsalah.wwweather

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherService {
    private const val URL = "https://api.openweathermap.org/data/2.5/"

    fun getWeatherService() : WeatherApi{
        return Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build()
                .create(WeatherApi::class.java)
    }

}