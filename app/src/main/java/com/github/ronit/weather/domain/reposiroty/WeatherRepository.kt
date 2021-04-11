package com.github.ronit.weather.domain.reposiroty

import com.github.ronit.weather.data.Forecast
import com.github.ronit.weather.data.Report
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun currentWeatherData(city: String, apiKey: String): Flow<Report>

    fun fiveDayForecastData(city: String, apiKey: String): Flow<Forecast>

}