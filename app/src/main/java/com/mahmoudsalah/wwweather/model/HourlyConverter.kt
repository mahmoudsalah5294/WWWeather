package com.mahmoudsalah.wwweather.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class HourlyConverter {

    @TypeConverter
    public fun toString(hourly: List<Hourly>):String{
        return Gson().toJson(hourly)
    }


    @TypeConverter
    public fun  toHourlyList(gson: String):List<Hourly>{
        val listType: Type = object : TypeToken<ArrayList<Hourly?>?>() {}.type
        return Gson().fromJson(gson,listType)
    }
}