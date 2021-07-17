package com.mahmoudsalah.wwweather.model

import androidx.room.TypeConverters

data class Alerts(
    @TypeConverters(AlertsConverter::class)
    val alerts: List<AlertDetails>
)