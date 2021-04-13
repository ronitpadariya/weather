package com.github.ronit.weather.ui.weatherinfo

import com.github.ronit.weather.data.Forecast
import com.github.ronit.weather.data.Report

data class ForecastViewIntent(
    val forecast: Forecast?,
    val error: String?,
    val isSuccess: Boolean
)

data class CurrentViewIntent(
    val report: Report?,
    val error: String?,
    val isSuccess: Boolean
)

data class FiveDayWeather(
    val dt: Long,
    val temp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val weatherID: Int,
    val timestampStart: Long,
    val timestampEnd: Long
)