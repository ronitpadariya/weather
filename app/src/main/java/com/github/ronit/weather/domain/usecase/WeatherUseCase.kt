package com.github.ronit.weather.domain.usecase

import com.github.ronit.weather.domain.reposiroty.WeatherRepository
import com.google.android.gms.common.api.internal.ApiKey
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    fun currentWeatherData(city: String, apiKey: String) = weatherRepository.currentWeatherData(city, apiKey)

    fun fiveDayForecastData(city: String, apiKey: String, units: String) = weatherRepository.fiveDayForecastData(city, apiKey, units)

}