package com.mahmoudsalah.wwweather.model

import androidx.room.Embedded
import androidx.room.TypeConverters

//@Embedded(prefix = "weather_")
//val weather: List<Weather>,
data class Current(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val uvi: Double,
    @TypeConverters(WeatherConverter::class)
    val weather: List<Weather>,
    val visibility: Int,
    val wind_deg: Int,
    val wind_speed: Double
)