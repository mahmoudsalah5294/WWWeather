package com.mahmoudsalah.wwweather


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import com.mahmoudsalah.wwweather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    companion object{
        val TAG = "MAHMOUD123"
    }

    lateinit var binding: ActivityMainBinding
    val bundle = Bundle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navView: BottomNavigationView = binding.navView
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val latitude = bundle.getString("latitude")
        val longitude = bundle.getString("longitude")
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_favorite,
                R.id.navigation_alerts, R.id.navigation_settings
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }








    }








