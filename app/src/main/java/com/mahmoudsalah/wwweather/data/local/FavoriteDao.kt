package com.mahmoudsalah.wwweather.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mahmoudsalah.wwweather.model.Favorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: Favorite)

    @Query("select * from favorite_table")
    suspend fun getFavorite(): List<Favorite>

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}