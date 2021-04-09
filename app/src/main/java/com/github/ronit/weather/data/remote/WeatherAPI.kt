package com.github.ronit.weather.data.remote

import retrofit2.Retrofit
import retrofit2.create

interface WeatherAPI {

    companion object {
        operator fun invoke(retrofit: Retrofit) = retrofit.create<WeatherAPI>()
    }

}