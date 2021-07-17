package com.mahmoudsalah.wwweather.ui.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AlertFactory(val alertRepo: AlertRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlertsViewModel(alertRepo) as T
    }
}