package com.mahmoudsalah.wwweather.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mahmoudsalah.wwweather.model.Alert
import com.mahmoudsalah.wwweather.model.SimpleDateConverter

@Database(entities = arrayOf(Alert::class), version = 1)
public abstract class AlertsDatabase:RoomDatabase() {
    abstract fun alertDao():AlertsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        // @Volatile
        private var INSTANCE: AlertsDatabase? = null

        fun getAlertData(context: Context): AlertsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(context.applicationContext, AlertsDatabase::class.java, "alerts_database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
        }
    }
}