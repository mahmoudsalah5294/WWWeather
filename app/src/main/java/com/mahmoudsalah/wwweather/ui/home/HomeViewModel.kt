package com.mahmoudsalah.wwweather.ui.home

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.lifecycle.*
import com.mahmoudsalah.wwweather.util.MyApplication
import com.mahmoudsalah.wwweather.data.local.WeatherDatabase
import com.mahmoudsalah.wwweather.data.remotely.Repo
import com.mahmoudsalah.wwweather.model.WeatherModel
import com.mahmoudsalah.wwweather.util.CheckConnectivity
import kotlinx.coroutines.*
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    val repo: Repo
    val locationMutableList = MutableLiveData<List<Double>>()
    var appSettings =  mutableListOf<String>()
    init {
        val weatherDao = WeatherDatabase.getWeatherData(MyApplication.getContext()).roomDao()
        val locationSharedPreferences = MyApplication.getContext().getSharedPreferences("MyLocation", Context.MODE_PRIVATE)
        val settingsSharedPreferences = MyApplication.getContext().getSharedPreferences("settingsConfig", Context.MODE_PRIVATE)
        repo = Repo(weatherDao, locationSharedPreferences,settingsSharedPreferences)
        getMyLocation()

        appSettings = getSettings()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getCurrentWeather(lat: Double, lon: Double) {

        Log.i("MAHMOUD", "homeViewMode: " + lat + ":" + lon)
        viewModelScope.launch {
            if (CheckConnectivity().isOnline()) {
                val service = repo.getURIConnect(lat.toString(), lon.toString(), appSettings[0],appSettings[1])
                withContext(Dispatchers.Main) {
                    if (service.isSuccessful) {
                        weatherLiveData.postValue(service.body())
                        service.body()?.let { savedWeatherData(it) }
                    } else {
                        Log.i("SAMY", "here")
                        errorLiveData.postValue("Loading Error")

//                    weatherLiveData.addSource()
                    }
                }

            } else {
                weatherLiveData.postValue(repo.getWeatherDatabase()[0])
            }
//            val service = repo.getCurrentWeatherData()


        }
    }

    fun getSettings(): MutableList<String> {
        return repo.getSettings()
    }
    fun savedWeatherData(weatherModel: WeatherModel) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.addWeatherData(weatherModel)
        }
    }

    fun getMyLocation() {
        viewModelScope.launch {
            val myLocation = repo.getLocationFromSharedPreferences()
            locationMutableList.postValue(myLocation)
        }
    }

    val errorLiveData = MutableLiveData<String>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val weatherLiveData = MutableLiveData<WeatherModel>()


}