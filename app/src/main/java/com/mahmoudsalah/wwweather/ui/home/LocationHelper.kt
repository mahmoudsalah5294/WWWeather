package com.mahmoudsalah.wwweather.ui.home
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
class LocationHelper constructor(var activity: Activity,var getLoction: GetLocation) {

    var TAG="main"

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object{

        val PERMISSION_ID=55
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {


        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(activity)


        if(checkPermissions()){

            if(isLocationEnabled()){

                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(activity) {
                    location ->

                        if (location==null){
                            Log.i("GPS", "getLastLocation: i am here")
                            requestNewLoactionData()
                        }else {
                            /////use loction
                            Log.i("GPS", "else: " + "done")
//                            getLoction.onLoctionResult(location)
                            requestNewLoactionData()
                        }

                }

            }else{

                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                activity.applicationContext.startActivity(intent);
            }

        }else{

            requestPermission();

        }

    }



    fun  checkPermissions():Boolean{
        if(ActivityCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false

    }



    fun requestPermission(){

        ActivityCompat.requestPermissions(activity , arrayOf<String>(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID);
    }

    fun isLocationEnabled():Boolean{
        val locationManager: LocationManager =activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER);
    }


    @SuppressLint("MissingPermission")
    private fun requestNewLoactionData(){  //get fresh location

        val locationRequest = LocationRequest()
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setInterval(15000)
        locationRequest.setNumUpdates(2)
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallBack, Looper.myLooper())

    }


    val locationCallBack: LocationCallback =object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)

            val location=p0.lastLocation
            //(context as GetLocation).onLoctionResult(location)

            getLoction.onLoctionResult(location)

        }
    }

}