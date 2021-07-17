package com.mahmoudsalah.wwweather.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mahmoudsalah.wwweather.model.*

@Database(entities = arrayOf(WeatherModel::class), version = 1)
@TypeConverters(FeelLikeConverter::class,TempConverter::class,WeatherConverter::class,HourlyConverter::class,DailyConverter::class)
public abstract class WeatherDatabase:RoomDatabase(){

    abstract fun roomDao(): WeatherDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        // @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getWeatherData(context: Context): WeatherDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(context.applicationContext, WeatherDatabase::class.java, "weather_database").build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
        }
    }
}



















//@Database(
//        entities  = [WeatherModel::class],
//        version = 1
//)
//@TypeConverters(CurrentConverter::class,DailyConverter::class,HourlyConverter::class)
//
//abstract class WeatherDatabase:RoomDatabase() {
//
//    abstract fun currentWeather():WeatherDao
//
//    companion object {
//        // Singleton prevents multiple instances of database opening at the
//        // same time.
//        @Volatile
//        private var INSTANCE: WeatherDatabase? = null
//
//        fun getWeatherData(context: Context): WeatherDatabase {
//            val tmp = INSTANCE
//            if (tmp != null) {
//                return tmp
//            }
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    WeatherDatabase::class.java,
//                    "weather_database"
//                ).build()
//                INSTANCE = instance
//                // return instance
//                instance
//            }
//        }
//    }







//    companion object{
//        @Volatile private var instance:WeatherDatabase? = null
//
////        private val lock = Any()
////
////        operator fun invoke(context: Context) = instance?: synchronized(lock){
////            instance?: buildDatabase(context).also { instance = it }
////        }
//
//        fun getWeatherData(context: Context): WeatherDatabase {
//            val tmp = instance
//            if (tmp != null){
//                return tmp
//            }
//            synchronized(context){
//                val inst  = Room.databaseBuilder(context.applicationContext,WeatherDatabase::class.java
//                        ,"weather_database").build()
//                instance = inst
//                return inst
//            }
//        }
//
////        private fun buildDatabase(context: Context) =
////                Room.databaseBuilder(context.applicationContext,WeatherDatabase::class.java
////                        ,"wwweather.db").build()
//
//    }
//}