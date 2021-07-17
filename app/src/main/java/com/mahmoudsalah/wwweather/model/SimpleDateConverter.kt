package com.mahmoudsalah.wwweather.model

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.google.gson.Gson

class SimpleDateConverter {
    @TypeConverter
    public fun toString(simpleDateFormat: SimpleDateFormat ):String{
        return Gson().toJson(simpleDateFormat);
    }


    @RequiresApi(Build.VERSION_CODES.N)
    @TypeConverter
    public fun  toSimpleDateFormat(gson:String ):SimpleDateFormat{

        return Gson().fromJson(gson,SimpleDateFormat::class.java)
    }
}