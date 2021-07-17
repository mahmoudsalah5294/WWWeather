package com.mahmoudsalah.wwweather.ui.alerts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudsalah.wwweather.model.Alert
import kotlinx.coroutines.launch

class AlertsViewModel(val alertRepo: AlertRepo) : ViewModel() {
    val alertMutableLiveData = MutableLiveData<MutableList<Alert>>()
    init {
        getData()
    }
    fun getData() {
        viewModelScope.launch {
            val favoriteList = alertRepo.alertsDao.getAlerts()
            alertMutableLiveData.postValue(favoriteList.toMutableList())

        }
    }

    fun deleteFavorite(alert: Alert){
        viewModelScope.launch { alertRepo.alertsDao.deleteAlert(alert) }
        val list = alertMutableLiveData.value
        list?.remove(alert)
        alertMutableLiveData.postValue(list!!)
    }

    fun addFavorite(alert: Alert) {

        viewModelScope.launch {
            alertRepo.addAlert(alert)
        }
    }
}