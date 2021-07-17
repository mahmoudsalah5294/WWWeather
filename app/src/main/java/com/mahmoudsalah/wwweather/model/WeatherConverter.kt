package com.mahmoudsalah.wwweather.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class WeatherConverter {
    @TypeConverter
    public fun toString(weather: List<Weather>):String{
        return Gson().toJson(weather)
    }


    @TypeConverter
    public fun  toArrayList(gson: String):List<Weather>{
        val listType: Type = object : TypeToken<ArrayList<Weather?>?>() {}.type
        return Gson().fromJson(gson,listType)
    }
}