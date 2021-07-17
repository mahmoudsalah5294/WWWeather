package com.mahmoudsalah.wwweather.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavoriteFactory(val favoriteRepo: FavoriteRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteViewModel(favoriteRepo) as T
    }
}