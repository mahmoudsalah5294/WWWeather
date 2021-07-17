package com.mahmoudsalah.wwweather.ui.home

import android.location.Location

interface GetLocation {

    fun onLoctionResult(location:Location)
}