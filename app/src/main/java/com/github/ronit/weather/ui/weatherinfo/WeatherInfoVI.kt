package com.github.ronit.weather.ui.weatherinfo

import com.github.ronit.weather.data.Forecast

data class ViewIntent(
    val forecast: Forecast?,
    val error: String?,
    val isSuccess: Boolean
)