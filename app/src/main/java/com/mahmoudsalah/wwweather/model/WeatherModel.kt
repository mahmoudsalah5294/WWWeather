package com.mahmoudsalah.wwweather.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable


const val weatherID = 1
@Entity(tableName = "current_weather")
data class WeatherModel(
    @Embedded(prefix = "current_")
    val current: Current,
    @TypeConverters(DailyConverter::class)
    val daily: List<Daily>,
    @TypeConverters(HourlyConverter::class)
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    @PrimaryKey(autoGenerate = false)
    var id:Int = weatherID) : Serializable

