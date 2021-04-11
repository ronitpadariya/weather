package com.github.ronit.weather.ui.weatherinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ronit.weather.di.APIKey
import com.github.ronit.weather.domain.usecase.WeatherUseCase
import com.github.ronit.weather.ui.home.LocationDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherInfoVM @Inject constructor(private val weatherUseCase: WeatherUseCase, @APIKey val apiKey: String): ViewModel() {

    private val viLiveData = MutableLiveData<ViewIntent>()
    val forecastLiveData: LiveData<ViewIntent> = viLiveData

    fun getForecastInfo(city: String){
        viewModelScope.launch {
            weatherUseCase.fiveDayForecastData(city, apiKey)
                .catch { e ->
                    viLiveData.postValue(ViewIntent(null, e.message, false))
                }
                .collect {
                    viLiveData.postValue(ViewIntent(it, null, true))
                }
        }
    }

}