package com.mahmoudsalah.wwweather.ui.favorite

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.mahmoudsalah.wwweather.R
import com.mahmoudsalah.wwweather.data.local.FavoriteDao
import com.mahmoudsalah.wwweather.data.local.FavoriteDatabase
import com.mahmoudsalah.wwweather.databinding.FragmentFavoriteBinding
import com.mahmoudsalah.wwweather.model.Favorite
import com.mahmoudsalah.wwweather.ui.home.GetLocation
import com.mahmoudsalah.wwweather.ui.home.HomeFragment
import com.mahmoudsalah.wwweather.ui.home.LocationHelper
import com.mahmoudsalah.wwweather.util.CheckConnectivity
import com.mahmoudsalah.wwweather.util.MyApplication
import es.dmoral.toasty.Toasty

class FavoriteFragment : Fragment(),ClickFavorite,GetLocation {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteBinding: FragmentFavoriteBinding
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private val TAG = "MAHMOUD999"
    lateinit var favoriteList: MutableList<Favorite>
    lateinit var favoriteDao: FavoriteDao
    lateinit var favoriteRepo: FavoriteRepo
    lateinit var favoriteAdapter: FavoriteAdapter
    lateinit var locationHelper: LocationHelper
    lateinit var sharedPreferences:SharedPreferences
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        favoriteDao = FavoriteDatabase.getFavoriteData(requireActivity()).favoriteDao()
        favoriteRepo = FavoriteRepo(favoriteDao)
        favoriteViewModel =
                ViewModelProvider(this, FavoriteFactory(favoriteRepo)).get(FavoriteViewModel::class.java)
        favoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater)

        sharedPreferences = requireContext().getSharedPreferences("MyLocation", Context.MODE_PRIVATE)

        val checkConnectivity = CheckConnectivity()

        if (!checkConnectivity.isOnline()){
            Toasty.error(requireContext(), "Please check your internet connection", Toast.LENGTH_LONG, true).show()

        }
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), MyApplication.googleKey)
        }
        favoriteBinding.currentLocationBtn.setOnClickListener {
            locationHelper = LocationHelper(requireActivity(),this)
            locationHelper.getLastLocation()
        }
        favoriteBinding.floatingActionButton.setOnClickListener {

            favoriteList = mutableListOf<Favorite>()
            //autocomplete

            val fieldList = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(requireContext())
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }
        favoriteViewModel.favoriteMutableLiveData.observe(viewLifecycleOwner, Observer {
           favoriteAdapter.data=it.toList()
            favoriteAdapter.notifyDataSetChanged()
        })

        favoriteAdapter = FavoriteAdapter(mutableListOf(),this)
//        favoriteAdapter.onClick(this)

        favoriteBinding.favoriteRecyclerview.apply {
            favoriteBinding.favoriteRecyclerview.layoutManager = LinearLayoutManager(requireContext().applicationContext, LinearLayoutManager.VERTICAL, false)
            adapter =favoriteAdapter
            adapter?.notifyDataSetChanged()


        }


        return favoriteBinding.root
    }


//    private fun setData(favorites: List<Favorite>?) {
//        favorites?.let {
//            favoriteBinding.favoriteRecyclerview.apply {
//                favoriteBinding.favoriteRecyclerview.layoutManager = LinearLayoutManager(requireContext().applicationContext, LinearLayoutManager.VERTICAL, false)
//                Log.i(TAG, "setData: " + it.toString())
////                adapter = FavoriteAdapter(it)
//                adapter?.notifyDataSetChanged()
//
//
//            }
//        }
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "onActivityResult: ")
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            Log.i(TAG, "onActivityResultAfterIF: ")
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
//                        Log.i(TAG, "Place: ${place.name}, ${place.latLng}")

                        val latitude = place.latLng!!.latitude
                        val longitude = place.latLng!!.longitude
                        val name = place.name!!

                        val favorite = Favorite(name, latitude, longitude)
                        favoriteViewModel.addFavorite(favorite)
                        favoriteViewModel.getData()
                        favoriteBinding.favoriteRecyclerview.adapter?.notifyDataSetChanged()

                    }
                }

                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i(TAG, status.statusMessage)
                    }
                }
                Activity.RESULT_CANCELED -> {
                    Log.i(TAG, "Result_Cancel: ")
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        favoriteViewModel.getData()
    }

    override fun deleteItem(favorite: Favorite) {


        favoriteViewModel.deleteFavorite(favorite)
    }

    override fun clickItem(favorite: Favorite) {
        val locationCoordinate = "${favorite.latitude}:${favorite.longitude}"
        sharedPreferences.edit().putString("MyCoordinate", locationCoordinate).apply()
        view?.findNavController()?.navigate(R.id.action_navigation_favorite_to_navigation_settings)
    }

    override fun onLoctionResult(location: Location) {
        val locationCoordinate = "${location.latitude}:${location.longitude}"
        sharedPreferences.edit().putString("MyCoordinate", locationCoordinate).apply()
        view?.findNavController()?.navigate(R.id.action_navigation_favorite_to_navigation_settings)
    }

}