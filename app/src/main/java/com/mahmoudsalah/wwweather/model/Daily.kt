package com.mahmoudsalah.wwweather.model

import androidx.room.Embedded
import androidx.room.TypeConverters

data class Daily(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    @TypeConverters(FeelLikeConverter::class)
    val feels_like: FeelsLike,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    @TypeConverters(TempConverter::class)
    val temp: Temp,
    val uvi: Double,
    @TypeConverters(WeatherConverter::class)
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_speed: Double
)