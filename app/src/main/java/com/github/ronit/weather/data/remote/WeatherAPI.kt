package com.github.ronit.weather.data.remote

import com.github.ronit.weather.data.Forecast
import com.github.ronit.weather.data.Report
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("/data/2.5/weather")
    suspend fun currentWeatherData(@Query("q") city: String, @Query("appid") apiKey: String): Report

    @GET("/data/2.5/forecast")
    suspend fun fiveDayForecastData(@Query("q") city: String, @Query("appid") apiKey: String): Forecast

    companion object {
        operator fun invoke(retrofit: Retrofit) = retrofit.create<WeatherAPI>()
    }

}