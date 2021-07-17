package com.mahmoudsalah.wwweather.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class CurrentConverter {

        @TypeConverter
        public fun toString(current: Current ):String{
            return Gson().toJson(current);
        }


        @TypeConverter
        public fun  toCurrent(gson:String ):Current{

            return Gson().fromJson(gson,Current::class.java);
        }

}