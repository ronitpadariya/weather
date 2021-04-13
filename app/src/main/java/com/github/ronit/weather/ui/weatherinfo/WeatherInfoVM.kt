package com.github.ronit.weather.ui.weatherinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.ronit.weather.domain.usecase.WeatherUseCase
import com.github.ronit.weather.ui.home.LocationDetails
import com.github.ronit.weather.utility.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherInfoVM @Inject constructor(private val weatherUseCase: WeatherUseCase): ViewModel() {

    private val mForecastLiveData = MutableLiveData<ForecastViewIntent>()
    val forecastLiveData: LiveData<ForecastViewIntent> = mForecastLiveData

    private val mCurrentLiveData = MutableLiveData<CurrentViewIntent>()
    val currentLiveData: LiveData<CurrentViewIntent> = mCurrentLiveData

    fun getForecastInfo(city: String){
        viewModelScope.launch {
            weatherUseCase.fiveDayForecastData(city, Constant.API_KEY, Constant.UNITS)
                .catch { e ->
                    mForecastLiveData.postValue(ForecastViewIntent(null, e.message, false))
                }
                .collect {
                    mForecastLiveData.postValue(ForecastViewIntent(it, null, true))
                }
        }
    }


    fun getCurrentWeatherInfo(city: String){
        viewModelScope.launch {
            weatherUseCase.currentWeatherData(city, Constant.API_KEY)
                .catch { e ->
                    mCurrentLiveData.postValue(CurrentViewIntent(null, e.message, false))
                }
                .collect {
                    mCurrentLiveData.postValue(CurrentViewIntent(it, null, true))
                }
        }
    }

}