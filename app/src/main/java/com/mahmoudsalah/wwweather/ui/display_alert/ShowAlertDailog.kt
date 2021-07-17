package com.mahmoudsalah.wwweather.ui.display_alert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mahmoudsalah.wwweather.R
import com.mahmoudsalah.wwweather.data.local.AlertsDao
import com.mahmoudsalah.wwweather.model.AlertModelConverter
import com.mahmoudsalah.wwweather.model.Alerts
import com.mahmoudsalah.wwweather.ui.alerts.AlertRepo
import com.tapadoo.alerter.Alerter
import kotlinx.coroutines.*

class ShowAlertDailog : AppCompatActivity() {
    lateinit var showAlertDailogViewModel: ShowAlertDailogViewModel
    lateinit var showAlertRepo: ShowAlertRepo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showAlertRepo = ShowAlertRepo()
        showAlertDailogViewModel = ViewModelProvider(this).get(ShowAlertDailogViewModel::class.java)
        showAlertDailogViewModel.alertMutableLiveData.observe(this, Observer {
            setAlert(it)})

            val alertIntent = intent.getStringExtra("alertModel")
            val converter = AlertModelConverter()



            alertIntent?.let { val alertModel = converter.toAlertModel(alertIntent)

                CoroutineScope(Dispatchers.IO).launch {
                val response = showAlertRepo.getURIConnect(alertModel.latitude.toString(), alertModel.longitude.toString(), "metric", "en")
                    withContext(Dispatchers.Main){
                        response?.let {

                        Alerter.create(this@ShowAlertDailog)
                                .setTitle(it.body()!!.alerts[1].event)
                                .setText(it.body()!!.alerts[1].description)
                                .addButton("Okay", R.style.AlertButton, View.OnClickListener {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        showAlertRepo.deleteFromDatabase(alertModel)
                                    }
                                    Toast.makeText(this@ShowAlertDailog, "Okay Clicked", Toast.LENGTH_LONG)
                                            .show()
                                    Alerter.hide()
                                })
                                .addButton("No", R.style.AlertButton, View.OnClickListener {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        showAlertRepo.deleteFromDatabase(alertModel)
                                    }
                                    Toast.makeText(this@ShowAlertDailog, "No Clicked", Toast.LENGTH_LONG).show()
                                    Alerter.hide()
                                }).enableInfiniteDuration(true).setSound()
                                .show()
                        }
                    }

                }


            }



    }

    private fun setAlert(it: Alerts) {
        Log.i("alert", "${it.alerts[0]}")
        it?.let {
            Log.i("alert", "setAlert: ${it.alerts[1].description}")
            Alerter.create(this)
                .setTitle(it.alerts[1].event)
                .setText(it.alerts[1].description)
                .addButton("Okay", R.style.AlertButton, View.OnClickListener {
                    Toast.makeText(this, "Okay Clicked", Toast.LENGTH_LONG)
                        .show()
                })
                .addButton("No", R.style.AlertButton, View.OnClickListener {
                    Toast.makeText(this, "No Clicked", Toast.LENGTH_LONG).show()
                })
                .show()
        }
    }
}