package com.mahmoudsalah.wwweather.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoudsalah.wwweather.model.Favorite
import com.mahmoudsalah.wwweather.model.WeatherModel
import com.mahmoudsalah.wwweather.model.weatherID

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(weatherData:WeatherModel)

    @Query("select * from current_weather")
     suspend fun getWeatherDatabase():List<WeatherModel>
}