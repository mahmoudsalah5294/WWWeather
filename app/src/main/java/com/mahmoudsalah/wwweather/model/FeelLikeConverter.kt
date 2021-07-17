package com.mahmoudsalah.wwweather.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class FeelLikeConverter {
    @TypeConverter
    public fun toString(feelsLike: FeelsLike):String{
        return Gson().toJson(feelsLike);
    }


    @TypeConverter
    public fun  toFeelLike(gson:String ):FeelsLike{

        return Gson().fromJson(gson,FeelsLike::class.java);
    }
}