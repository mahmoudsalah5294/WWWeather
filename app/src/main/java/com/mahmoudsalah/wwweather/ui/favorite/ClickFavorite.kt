package com.mahmoudsalah.wwweather.ui.favorite

import android.location.Location
import com.mahmoudsalah.wwweather.model.Favorite

interface ClickFavorite {
    fun deleteItem(favorite: Favorite)
    fun clickItem(favorite: Favorite)
}