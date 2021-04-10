package com.github.ronit.weather.ui.addcity

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ronit.weather.utility.LocationAddress
import com.github.ronit.weather.utility.OnFindCityListener

class AddCityViewModel : ViewModel() {

    private val mCityLiveData = MutableLiveData<String>()
    val cityLiveData: LiveData<String> = mCityLiveData

    fun findCityBasedOnLatLng(latitude: Double, longitude: Double, context: Context){
        val locationAddress = LocationAddress()
        locationAddress.getAddressFromLocation(latitude, longitude, context, object : OnFindCityListener{
            override fun onFindCity(city: String) {
                mCityLiveData.postValue(city)
            }
        })
    }

}