package com.mahmoudsalah.wwweather.ui.alerts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.mahmoudsalah.wwweather.model.Alert
import com.mahmoudsalah.wwweather.model.AlertModelConverter
import com.mahmoudsalah.wwweather.ui.display_alert.ShowAlertDailog
import com.mahmoudsalah.wwweather.ui.display_alert.ShowAlertDailogViewModel
import com.mahmoudsalah.wwweather.util.MyApplication

class MyBrodCastReciver : BroadcastReceiver() {

    //    lateinit var repo: Repo
    lateinit var alertModel: Alert

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        val alertType = intent?.getStringExtra("type")
        if (alertType.equals("alert")) {

            val alert = intent?.getStringExtra("alertModel")
            val converter = AlertModelConverter()
            alert?.let { alertModel = converter.toAlertModel(it) }

//        alertModel?.let {
//            CoroutineScope(Dispatchers.IO).launch {
//                val response = repo.getURIConnect(alertModel.latitude.toString(), alertModel.longitude.toString(), "metric", "en")
//            }

            val showAlertDailogViewModel = ShowAlertDailogViewModel()
            alertModel?.let {
                showAlertDailogViewModel.getCurrentWeather(alertModel.latitude, alertModel.longitude)
                val activityIntent = Intent("android.intent.action.MAIN")
                activityIntent.setClass(context!!, ShowAlertDailog::class.java)
                activityIntent.putExtra("alertModel", intent?.getStringExtra("alertModel"))
//            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(activityIntent)
            }

        } else {
            Log.i("mama", "onReceive: ")
            val notificationIntent = Intent(context, NotificationJobIntentService::class.java)
            NotificationJobIntentService.enqueueWork(context?:MyApplication.getContext(),notificationIntent)

        }
    }
}