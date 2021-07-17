package com.mahmoudsalah.wwweather.ui.alerts

import com.mahmoudsalah.wwweather.data.local.AlertsDao
import com.mahmoudsalah.wwweather.model.Alert

class AlertRepo(val alertsDao: AlertsDao) {


    suspend fun getAlertDatabase(): List<Alert> {
        return alertsDao.getAlerts()
    }

    suspend fun addAlert(alert: Alert){
        alertsDao.addAlert(alert)
    }

    suspend fun deleteAlert(alert: Alert){
        alertsDao.deleteAlert(alert)
    }
}