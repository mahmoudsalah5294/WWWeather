package com.mahmoudsalah.wwweather.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class AlertsConverter {

    @TypeConverter
    public fun toString(alertDetails: AlertDetails):String{
        return Gson().toJson(alertDetails)
    }


    @TypeConverter
    public fun  toAlertDetails(gson:String?):AlertDetails{

        return Gson().fromJson(gson,AlertDetails::class.java)
    }
}