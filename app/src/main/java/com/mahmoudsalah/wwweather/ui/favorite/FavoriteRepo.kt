package com.mahmoudsalah.wwweather.ui.favorite

import com.mahmoudsalah.wwweather.data.local.FavoriteDao
import com.mahmoudsalah.wwweather.data.local.FavoriteDatabase
import com.mahmoudsalah.wwweather.model.Favorite

class FavoriteRepo(val favoriteDao: FavoriteDao) {


    suspend fun getAllFavorite(): List<Favorite> {
        return favoriteDao.getFavorite()
    }

    suspend fun removeFavorite(favorite: Favorite){
        return favoriteDao.deleteFavorite(favorite)
    }

    suspend fun addFavorite(favorite: Favorite) {
         favoriteDao.addFavorite(favorite)
    }
}