package com.mahmoudsalah.wwweather.ui.display_alert

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudsalah.wwweather.model.Alerts
import com.mahmoudsalah.wwweather.util.CheckConnectivity
import com.mahmoudsalah.wwweather.util.MyApplication
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowAlertDailogViewModel:ViewModel() {

    val alertMutableLiveData = MutableLiveData<Alerts>()
    val showAlertRepo: ShowAlertRepo
    init {
        showAlertRepo = ShowAlertRepo()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getCurrentWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            if (CheckConnectivity().isOnline()) {
                val service = showAlertRepo.getURIConnect(lat.toString(), lon.toString(),"metric","en")
                withContext(Dispatchers.Main) {
                    if (service.isSuccessful) {
                        alertMutableLiveData.postValue(service.body())
//                        service.body()?.let { savedWeatherData(it) }
                    } else {
                        Log.i("alert", "getCurrentWeather: not successful")
                    }
                }

            } else {
                Toasty.error(MyApplication.getContext(),"No Internet Connection").show()
            }



        }

    }
//    fun getSettings(): MutableList<String> {
//        return repo.getSettings()
//    }
}