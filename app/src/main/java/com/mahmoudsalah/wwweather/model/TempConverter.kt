package com.mahmoudsalah.wwweather.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class TempConverter {

    @TypeConverter
    public fun toString(temp: Temp):String{
        return Gson().toJson(temp);
    }


    @TypeConverter
    public fun  toFeelLike(gson:String ):Temp{

        return Gson().fromJson(gson,Temp::class.java);
    }
}