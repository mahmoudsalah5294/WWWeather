package com.mahmoudsalah.wwweather.ui.favorite


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudsalah.wwweather.model.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(val favoriteRepo: FavoriteRepo) : ViewModel() {
    val favoriteMutableLiveData = MutableLiveData<MutableList<Favorite>>()

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            val favoriteList = favoriteRepo.favoriteDao.getFavorite()
            favoriteMutableLiveData.postValue(favoriteList.toMutableList())

        }
    }

    fun deleteFavorite(favorite: Favorite){
        viewModelScope.launch { favoriteRepo.favoriteDao.deleteFavorite(favorite) }
        val list = favoriteMutableLiveData.value
        list?.remove(favorite)
        favoriteMutableLiveData.postValue(list!!)
    }

    fun addFavorite(favorite: Favorite) {

        viewModelScope.launch {
            favoriteRepo.addFavorite(favorite)
        }
    }


}