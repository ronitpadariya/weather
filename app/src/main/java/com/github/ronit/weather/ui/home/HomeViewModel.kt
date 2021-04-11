package com.github.ronit.weather.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ronit.weather.domain.usecase.WeatherUseCase
import com.github.ronit.weather.utility.LocationAddress
import com.github.ronit.weather.utility.OnFindCityListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherUseCase: WeatherUseCase): ViewModel() {

    private val mCityLiveData = MutableLiveData<LocationDetails>()
    val cityLiveData: LiveData<LocationDetails> = mCityLiveData

    fun findCityBasedOnLatLng(latitude: Double, longitude: Double, context: Context){
        val locationAddress = LocationAddress()
        locationAddress.getAddressFromLocation(latitude, longitude, context, object :
            OnFindCityListener {
            override fun onFindCity(city: String) {
                mCityLiveData.postValue(LocationDetails(city, latitude, longitude))
            }
        })
    }

}