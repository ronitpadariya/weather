package com.github.ronit.weather.data

import com.github.ronit.weather.data.remote.WeatherAPI
import com.github.ronit.weather.domain.reposiroty.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(private val weatherAPI: WeatherAPI
): WeatherRepository {

    override fun currentWeatherData(city: String, apiKey: String) = flow { emit(weatherAPI.currentWeatherData(city, apiKey)) }

    override fun fiveDayForecastData(city: String, apiKey: String) = flow { emit(weatherAPI.fiveDayForecastData(city, apiKey)) }
}