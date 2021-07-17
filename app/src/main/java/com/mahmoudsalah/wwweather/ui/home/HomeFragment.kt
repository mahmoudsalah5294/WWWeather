package com.mahmoudsalah.wwweather.ui.home


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudsalah.wwweather.DailyAdapter
import com.mahmoudsalah.wwweather.HourlyAdapter
import com.mahmoudsalah.wwweather.databinding.FragmentHomeBinding
import com.mahmoudsalah.wwweather.model.WeatherModel
import com.mahmoudsalah.wwweather.util.CheckConnectivity
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    companion object{
        val TAG = "MAHMOUD"
    }
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

//    lateinit var locationHelper: LocationHelper
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
): View? {
       homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        observeViewModel(homeViewModel)

        val checkConnectivity = CheckConnectivity()

        if (!checkConnectivity.isOnline()){
            Toasty.error(requireContext(), "Please check your internet connection", Toast.LENGTH_LONG, true).show()

        }

//        locationHelper = LocationHelper(requireActivity(),this)
//        locationHelper.getLastLocation()

//        homeViewModel.getCurrentWeather(30.044281,31.340002)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }



    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeViewModel(viewModel: HomeViewModel) {
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer { handleError(it) })
        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer { setData(it) })
        viewModel.locationMutableList.observe(viewLifecycleOwner, Observer {
            setLocationModel(it)
        })

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setLocationModel(it: List<Double>?) {
        it?.let { latlng -> homeViewModel.getCurrentWeather(latlng.get(0), latlng.get(1))}
    }

    private fun handleError(it: String?) {
        Log.i("MAHMOUD", "handleError: " + it)
    }

    private fun setData(it: WeatherModel?) {
        it?.apply {

//            Log.i("Database", "setData: "+it.toString())
//            homeViewModel.savedWeatherData(it)
            val date = convertUnix(current.dt.toLong())

            binding.hourText.setText(hourFormat(date))
            binding.dayTxt.setText(dayFormat(date))
            binding.cityText.setText(it.timezone)
            binding.monthText.setText(monthFormat(date))
            binding.statusTxt.setText(current.weather[0].description)

            binding.tempNowTxt.text = current.temp.toInt().toString()
            val mainIcon:String = current.weather[0].icon
            val mainIconUrl = "http://openweathermap.org/img/w/$mainIcon.png"
            Picasso.get().load(mainIconUrl).into(binding.mainImage)

            val icon:String = current.weather[0].icon
            val iconUrl = "http://openweathermap.org/img/w/$icon.png"
            Picasso.get().load(iconUrl).into(binding.imageView)

            Log.i(TAG, "setData: " + current.temp)

            //Hour RecyclerView
            binding.hourRecyclerView.apply {
            binding.hourRecyclerView.layoutManager = LinearLayoutManager(
                    context.applicationContext,
                    LinearLayoutManager.HORIZONTAL,
                    false
            )
                adapter = HourlyAdapter(it.hourly)
            }

            binding.dayRecyclerView.apply {
                layoutManager = LinearLayoutManager(context.applicationContext)
                adapter = DailyAdapter(it.daily)

            }
            binding.numberTxt.text = current.humidity.toString()
            binding.numberTxt2.text = current.pressure.toString()
            binding.numberTxt3.text = current.wind_speed.toString()
            binding.numberTxt4.text = current.clouds.toString()


        }
    }

    fun convertUnix(unix: Long): Date {
        val date = Date(unix * 1000)
        return date
    }
    fun hourFormat(date: Date): String {
        val format = SimpleDateFormat("hh:mm a")
        format.setTimeZone(TimeZone.getTimeZone("GMT+02:00"))
        return format.format(date)
    }
    fun monthFormat(date: Date): String {
        val format = SimpleDateFormat("MMM dd")
        format.setTimeZone(TimeZone.getTimeZone("GMT"))
        return format.format(date)
    }
    fun dayFormat(date: Date): String {
        val format = SimpleDateFormat("EEEE")
        format.setTimeZone(TimeZone.getTimeZone("GMT"))
        return format.format(date)
    }


    //location



//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        Log.i("GPS", "onRequestPermissionsResult: called")
//        locationHelper.getLastLocation()
//
//    }

//    @RequiresApi(Build.VERSION_CODES.M)
//    override fun onLoctionResult(location: Location) {
//
//        homeViewModel.getCurrentWeather(location.latitude,location.longitude)
//        Log.i(TAG, "Latitude: "+location.latitude+" Longitude: "+location.longitude)
//    }
}