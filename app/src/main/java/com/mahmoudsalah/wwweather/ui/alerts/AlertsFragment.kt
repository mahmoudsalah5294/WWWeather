package com.mahmoudsalah.wwweather.ui.alerts

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudsalah.wwweather.R
import com.mahmoudsalah.wwweather.data.local.AlertsDao
import com.mahmoudsalah.wwweather.data.local.AlertsDatabase
import com.mahmoudsalah.wwweather.databinding.FragmentAlertsBinding
import com.mahmoudsalah.wwweather.model.Alert
import com.mahmoudsalah.wwweather.util.CheckConnectivity
import es.dmoral.toasty.Toasty

class AlertsFragment : Fragment(),ClickAlert {

    private lateinit var alertsViewModel: AlertsViewModel
    lateinit var alertsBinding: FragmentAlertsBinding
    lateinit var alertRepo: AlertRepo
    lateinit var alertsDao: AlertsDao
    lateinit var alertMutableList: MutableList<Alert>
    lateinit var alertsAdapter: AlertsAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        alertsDao = AlertsDatabase.getAlertData(requireActivity()).alertDao()
        alertRepo = AlertRepo(alertsDao)
        alertsViewModel =
                ViewModelProvider(this,AlertFactory(alertRepo)).get(AlertsViewModel::class.java)
        alertsBinding = FragmentAlertsBinding.inflate(layoutInflater)

        alertsBinding.floatingAlert.setOnClickListener { var dialog = AlertDialog()
        dialog.show(requireActivity().supportFragmentManager,"alertDialog")
        }
        alertMutableList = mutableListOf()

        val checkConnectivity = CheckConnectivity()

        if (!checkConnectivity.isOnline()){
            Toasty.error(requireContext(), "Please check your internet connection", Toast.LENGTH_LONG, true).show()

        }

        alertsViewModel.alertMutableLiveData.observe(viewLifecycleOwner, Observer {
            alertsAdapter.data = it.toList()
            alertsAdapter.notifyDataSetChanged()

        })



        alertsAdapter = AlertsAdapter(mutableListOf(),this)

        alertsBinding.alertRecyclerview.apply {
            alertsBinding.alertRecyclerview.layoutManager = LinearLayoutManager(requireContext().applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter =alertsAdapter
            adapter?.notifyDataSetChanged()


        }

        return alertsBinding.root
    }



    override fun onStart() {
        super.onStart()
        Log.i("track", "onStart: ")
        alertsViewModel.getData()
    }

    override fun onResume() {
        super.onResume()
        Log.i("track", "onResume: ")
    }

    override fun deleteItem(alert: Alert) {
        alertsViewModel.deleteFavorite(alert)
    }

    override fun editItem(alert: Alert) {

    }
}