package com.github.ronit.weather.data


import com.github.ronit.weather.BuildConfig
import com.github.ronit.weather.data.remote.WeatherAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object DataModule {

    fun provideUserApiService(retrofit: Retrofit): WeatherAPI = WeatherAPI(retrofit)

    fun provideMoshi(): Moshi {
        return Moshi
                .Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
    }

    fun provideRetrofit(
            baseUrl: String,
            moshi: Moshi,
            client: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(baseUrl)
                .build()
    }

    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(
                        HttpLoggingInterceptor()
                                .apply { level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }
                )
                .build()
    }



}