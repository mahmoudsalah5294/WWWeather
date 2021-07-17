package com.mahmoudsalah.wwweather.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mahmoudsalah.wwweather.model.Favorite
import com.mahmoudsalah.wwweather.model.WeatherModel

@Database(entities = arrayOf(Favorite::class), version = 1)
public abstract class FavoriteDatabase:RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        // @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        fun getFavoriteData(context: Context): FavoriteDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                    ?: synchronized(this) {
                        val instance = Room.databaseBuilder(context.applicationContext, FavoriteDatabase::class.java, "favorite_database").fallbackToDestructiveMigration().build()
                        INSTANCE = instance
                        // return instance
                        instance
                    }
        }
    }

}