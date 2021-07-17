package com.mahmoudsalah.wwweather.ui.alerts

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.room.Dao
import com.mahmoudsalah.wwweather.data.local.AlertsDao
import com.mahmoudsalah.wwweather.data.local.AlertsDatabase
import com.mahmoudsalah.wwweather.databinding.AlertCustomDialogBinding
import com.mahmoudsalah.wwweather.model.Alert
import com.mahmoudsalah.wwweather.util.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AlertDialog:DialogFragment() {

    lateinit var alertCustomDialogBinding: AlertCustomDialogBinding
    var type = ""
    lateinit var alertRepo: AlertRepo
    lateinit var alertDao: AlertsDao
    lateinit var sharedPreferences: SharedPreferences
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = requireContext().getSharedPreferences("MyLocation", Context.MODE_PRIVATE)
        alertCustomDialogBinding = AlertCustomDialogBinding.inflate(layoutInflater)
        alertDao = AlertsDatabase.getAlertData(requireActivity()).alertDao()
        alertRepo = AlertRepo(alertDao)
        alertCustomDialogBinding.timeFromTxt.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                alertCustomDialogBinding.timeFromTxt.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(requireActivity(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
        }

        alertCustomDialogBinding.dateFromTxt.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker,year,month,day ->
                cal.set(Calendar.DAY_OF_MONTH, day)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.YEAR,year)
                alertCustomDialogBinding.dateFromTxt.text = SimpleDateFormat("dd/MM/yyyy").format(cal.time)
            }
            DatePickerDialog(requireActivity(), dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        alertCustomDialogBinding.timeToTxt.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                alertCustomDialogBinding.timeToTxt.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(requireActivity(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
        }

        alertCustomDialogBinding.dateToTxt.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker,year,month,day ->
                cal.set(Calendar.DAY_OF_MONTH, day)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.YEAR,year)
                alertCustomDialogBinding.dateToTxt.text = SimpleDateFormat("dd/MM/yy").format(cal.time)
            }
            DatePickerDialog(requireActivity(), dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }


        val list = getLocationFromSharedPreferences()

        alertCustomDialogBinding.saveBtn.setOnClickListener{
            val checkedRadio = alertCustomDialogBinding.radioGroup.checkedRadioButtonId
            when(checkedRadio){
                alertCustomDialogBinding.alarmRadio.id-> type = "alarm"
                alertCustomDialogBinding.notiRadio.id-> type = "notification"
            }
            val alert = Alert(alertCustomDialogBinding.timeFromTxt.text.toString()
                ,alertCustomDialogBinding.dateFromTxt.text.toString(),
                alertCustomDialogBinding.timeToTxt.text.toString(),
                alertCustomDialogBinding.dateToTxt.text.toString()
                ,type,list[0],list[1])

            MainScope().launch {
                alertRepo.addAlert(alert)
            }
            val alarm = Alarm(MyApplication.getContext(),alert)
            alarm.setAlarm()
            this.dismiss()
        }
        return alertCustomDialogBinding.root
    }

    fun getLocationFromSharedPreferences(): List<Double> {
        val myLocation = sharedPreferences.getString("MyCoordinate","0.0:0.0")
        val latLng  = myLocation?.split(":")
        val lat = latLng?.get(0)?.toDouble()
        val lng = latLng?.get(1)?.toDouble()
        val list = listOf<Double>(lat?:0.0,lng?:0.0)
//        Log.i(SplashScreen.TAG, "onLoctionResult: "+lat+":"+lng)
        return list
    }
}