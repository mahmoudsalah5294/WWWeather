package com.mahmoudsalah.wwweather.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DailyConverter {
    @TypeConverter
    public fun toString(daily: List<Daily>):String{
        return Gson().toJson(daily)
    }


    @TypeConverter
    public fun  toDailyList(gson: String):List<Daily>{
//        val listType: Type = object : TypeToken<ArrayList<Daily?>?>() {}.type
        return Gson().fromJson(gson,Array<Daily>::class.java).asList()
    }
}