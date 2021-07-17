package com.mahmoudsalah.wwweather.data.remotely

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import com.mahmoudsalah.wwweather.SplashScreen
import com.mahmoudsalah.wwweather.WeatherService
import com.mahmoudsalah.wwweather.data.local.WeatherDao
import com.mahmoudsalah.wwweather.data.local.WeatherDatabase
import com.mahmoudsalah.wwweather.model.WeatherModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.collections.mutableListOf as mutableListOf


class Repo(val weatherDao: WeatherDao,val locationSharedPreferences: SharedPreferences,val settingsSharedPreferences: SharedPreferences) {

lateinit var service: Response<WeatherModel>
lateinit var weatherDatabase: WeatherDatabase


    fun getLocationFromSharedPreferences(): List<Double> {
        val myLocation = locationSharedPreferences.getString("MyCoordinate","0.0:0.0")
        val latLng  = myLocation?.split(":")
        val lat = latLng?.get(0)?.toDouble()
        val lng = latLng?.get(1)?.toDouble()
        val list = listOf<Double>(lat?:0.0,lng?:0.0)
//        Log.i(SplashScreen.TAG, "onLoctionResult: "+lat+":"+lng)
        return list
    }
    fun getSettings(): MutableList<String> {
        val unit = settingsSharedPreferences.getString("unit","metric")
        val language = settingsSharedPreferences.getString("language","en")
        return mutableListOf(unit?:"metric",language?:"en")
    }

    suspend fun getURIConnect(lat:String,lon:String,unit:String,lang:String): Response<WeatherModel> {
    Log.i("MAHMOUD", "Repo: "+lat+":"+lon)
    val url = "onecall?lat=$lat&lon=$lon&units=$unit&lang=$lang&exclude=minutely&appid=ef414347b1c0f0e2222a4525726ca01d"

    service = WeatherService.getWeatherService().getWeather(url)

    return service
}

 suspend fun addWeatherData(weatherModel: WeatherModel){
     Log.i("Database", "setData: "+weatherModel.toString())
    weatherDao.upsert(weatherModel)
}


    suspend fun getWeatherDatabase(): List<WeatherModel> {
        return weatherDao.getWeatherDatabase()
    }
//private fun savedData(){
//    if (service != null){
//        weatherDatabase = WeatherDatabase.getWeatherData(context)
//        service.body()?.let { weatherDatabase.currentWeather().upsert(it) }
//    }
//}
//    suspend fun getLocalData(): LiveData<WeatherModel> {
//        return weatherDatabase.currentWeather().getWeatherDatabase()
//    }


//    suspend fun getCurrentWeatherData(){
//        if (isOnline()){
//            getURIConnect(latitude,longitude,measure)
//        }else{
//            getLocalData()
//        }


    }



//    fun isOnline():Boolean{
//        val connectivityManger = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo  = connectivityManger.activeNetworkInfo
//        return networkInfo != null && networkInfo.isConnected
//    }

//}





//fun isOnline(context: Context): Boolean {
//    val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//    if (connectivityManager != null) {
//        val capabilities =
//                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
//        if (capabilities != null) {
//            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
//                Log.i("MAHMOUD", "NetworkCapabilities.TRANSPORT_CELLULAR")
//                return true
//            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
//                Log.i("MAHMOUD", "NetworkCapabilities.TRANSPORT_WIFI")
//                return true
//            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
//                Log.i("MAHMOUD", "NetworkCapabilities.TRANSPORT_ETHERNET")
//                return true
//            }
//        }
//    }
//    return false
//}


