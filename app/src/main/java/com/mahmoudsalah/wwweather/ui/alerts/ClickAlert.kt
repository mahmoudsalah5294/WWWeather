package com.mahmoudsalah.wwweather.ui.alerts

import com.mahmoudsalah.wwweather.model.Alert

interface ClickAlert {
    fun deleteItem(alert: Alert)
    fun editItem(alert: Alert)
}