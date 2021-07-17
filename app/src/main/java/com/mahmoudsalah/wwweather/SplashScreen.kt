package com.mahmoudsalah.wwweather

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.mahmoudsalah.wwweather.ui.home.GetLocation
import com.mahmoudsalah.wwweather.ui.home.HomeFragment
import com.mahmoudsalah.wwweather.ui.home.LocationHelper
import gr.net.maroulis.library.EasySplashScreen
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.runBlocking

class SplashScreen : AppCompatActivity(),GetLocation {
    lateinit var locationHelper: LocationHelper
    lateinit var sharedPreferences:SharedPreferences
    val PERMISSION_ID=55
    companion object{
        val TAG = "splashScreen"
    }
    val context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("MyLocation", Context.MODE_PRIVATE)
        locationHelper = LocationHelper(this,this)

//         Handler().postDelayed(Runnable {
//
//        },2000)


        requestPermission()
        locationHelper.getLastLocation()


                val splashScreen = EasySplashScreen(this).withFullScreen().withTargetActivity(
                    MainActivity::class.java
                )
                    .withSplashTimeOut(3000).withBackgroundResource(R.drawable.splah_screen)
                    .create()
                setContentView(splashScreen)


    }

    fun requestPermission(){

        ActivityCompat.requestPermissions(this , arrayOf<String>(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION), LocationHelper.PERMISSION_ID
        )
    }

    override fun onLoctionResult(location: Location) {

            val locationCoordinate = "${location.latitude}:${location.longitude}"
            sharedPreferences.edit().putString("MyCoordinate", locationCoordinate).apply()
            Log.i(TAG, "onLoctionResult: "+location.latitude+":"+location.longitude)

        }

    fun  checkPermissions():Boolean{
        if(ActivityCompat.checkSelfPermission(this.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED&&
            ActivityCompat.checkSelfPermission(this.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false

    }


}