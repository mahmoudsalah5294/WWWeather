package com.mahmoudsalah.wwweather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert_table")
data class Alert(
    val fromTime: String,
    val fromDate: String,
    val toTime: String,
    val toDate: String,
    val type:String,
    val latitude:Double,
    val longitude:Double,
    @PrimaryKey(autoGenerate = true)
    var id:Int  = 0)