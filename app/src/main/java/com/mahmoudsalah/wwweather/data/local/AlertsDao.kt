package com.mahmoudsalah.wwweather.data.local

import androidx.room.*
import com.mahmoudsalah.wwweather.model.Alert

@Dao
interface AlertsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAlert(alert: Alert)

    @Query("select * from alert_table")
    suspend fun getAlerts(): List<Alert>

    @Delete
    suspend fun deleteAlert(alert: Alert)
}