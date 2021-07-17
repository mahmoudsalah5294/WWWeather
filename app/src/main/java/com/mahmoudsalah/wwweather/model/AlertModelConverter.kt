package com.mahmoudsalah.wwweather.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class AlertModelConverter {
    @TypeConverter
    public fun toString(alert: Alert):String{
        return Gson().toJson(alert);
    }


    @TypeConverter
    public fun  toAlertModel(gson:String ):Alert{

        return Gson().fromJson(gson,Alert::class.java);
    }
}