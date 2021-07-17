package com.mahmoudsalah.wwweather.ui.settings

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoudsalah.wwweather.databinding.FragmentSettingsBinding
import java.util.*


class SettingsFragment : Fragment(),AdapterView.OnItemSelectedListener {

    private lateinit var settingsViewModel: SettingsViewModel
    lateinit var settingsBinding: FragmentSettingsBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
                ViewModelProvider(this).get(SettingsViewModel::class.java)

        settingsBinding = FragmentSettingsBinding.inflate(layoutInflater)

        sharedPreferences = requireContext().getSharedPreferences(
            "settingsConfig",
            Context.MODE_PRIVATE
        )

        val unit = sharedPreferences.getString("unit", "metric")
        val language = sharedPreferences.getString("language", "en")
        val notification = sharedPreferences.getString("notification", "off")

        var unitIndex = 0
        var languageIndex = 0
        var notificationIndex = 1
        when(unit){
            "metric" -> {
                unitIndex = 0
            }
            "imperial" -> {
                unitIndex = 1
            }
        }
        when(language){
            "en" -> {
                languageIndex = 0
            }
            "ar" -> {
                languageIndex = 1
            }
        }
        when(notification){
            "off" -> {
                notificationIndex = 1
            }
            "on" -> {
                notificationIndex = 0
            }
        }

        settingsBinding.unitSpinner.setSelection(unitIndex)
        settingsBinding.languageSpinner.setSelection(languageIndex)
        settingsBinding.notificationSpinner.setSelection(notificationIndex)


        settingsBinding.unitSpinner.onItemSelectedListener = this
        settingsBinding.languageSpinner.onItemSelectedListener = this
        settingsBinding.notificationSpinner.onItemSelectedListener = this




        return settingsBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

//        Log.i("spinner", "${parent?.getItemAtPosition()}")
        when (parent?.id) {
            settingsBinding.unitSpinner.id -> {
                when (parent.getItemAtPosition(position)) {
                    "Metric" -> {
                        sharedPreferences.edit().putString("unit", "metric").apply()
                    }
                    "Imperial" -> {
                        sharedPreferences.edit().putString("unit", "imperial").apply()
                    }
                }
            }
            settingsBinding.languageSpinner.id -> {
                when (parent.getItemAtPosition(position)) {
                    "English" -> {
                        sharedPreferences.edit().putString("language", "en").apply()
                        setLocale("en")
                    }
                    "Arabic" -> {
                        sharedPreferences.edit().putString("language", "ar").apply()
                        setLocale("ar")

                    }
                }
            }
            settingsBinding.notificationSpinner.id -> {
                sharedPreferences.edit().putString(
                    "notification", parent.getItemAtPosition(position).toString()
                ).apply()
            }
        }


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    fun setLocale(languageCode: String?) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = requireActivity().resources
        val config: Configuration = resources.getConfiguration()
        config.setLocale(Locale(languageCode?.toLowerCase()))
        resources.updateConfiguration(config, resources.getDisplayMetrics())
    }
}