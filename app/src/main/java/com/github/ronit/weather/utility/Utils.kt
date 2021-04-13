package com.github.ronit.weather.utility

import com.github.ronit.weather.data.Forecast
import com.github.ronit.weather.ui.weatherinfo.FiveDayWeather
import java.util.*

object Utils {



    fun invoke(forecast: Forecast): MutableList<FiveDayWeather> {
        val fiveDayWeatherList = mutableListOf<FiveDayWeather>()
        var day = 0
        forecast.list?.forEach { report ->
            val calendar = Calendar.getInstance(TimeZone.getDefault())
            val newCalendar: Calendar = addDays(calendar, day)
            val fiveDayWeather = FiveDayWeather(
                report.dt ?: 0,
                report.main?.temp ?: 0.toDouble(),
                report.main?.tempMin ?: 0.toDouble(),
                report.main?.tempMax ?: 0.toDouble(),
                report.weather?.get(0)?.id ?: 0,
                getStartOfDayTimestamp(newCalendar),
                getEndOfDayTimestamp(newCalendar)
            )
            fiveDayWeatherList.add(fiveDayWeather)
            day++
        }
        return fiveDayWeatherList
    }


    private fun addDays(cal: Calendar, days: Int): Calendar {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.timeInMillis = cal.timeInMillis
        calendar.add(Calendar.DATE, days)
        return calendar
    }

    fun getStartOfDayTimestamp(calendar: Calendar): Long {
        val newCalendar = Calendar.getInstance(TimeZone.getDefault())
        newCalendar.timeInMillis = calendar.timeInMillis
        newCalendar[Calendar.HOUR_OF_DAY] = 0
        newCalendar[Calendar.MINUTE] = 0
        newCalendar[Calendar.SECOND] = 0
        newCalendar[Calendar.MILLISECOND] = 0
        return newCalendar.timeInMillis
    }

    fun getEndOfDayTimestamp(calendar: Calendar): Long {
        val newCalendar = Calendar.getInstance(TimeZone.getDefault())
        newCalendar.timeInMillis = calendar.timeInMillis
        newCalendar[Calendar.HOUR_OF_DAY] = 23
        newCalendar[Calendar.MINUTE] = 59
        newCalendar[Calendar.SECOND] = 59
        newCalendar[Calendar.MILLISECOND] = 0
        return newCalendar.timeInMillis
    }

    fun getWeatherStatus(weatherCode: Int): String {
        when {
            weatherCode / 100 == 2 -> {
                Constant.WEATHER_STATUS[0]
            }
            weatherCode / 100 == 3 -> {
                Constant.WEATHER_STATUS[1]
            }
            weatherCode / 100 == 5 -> {
                Constant.WEATHER_STATUS[2]
            }
            weatherCode / 100 == 6 -> {
                Constant.WEATHER_STATUS[3]
            }
            weatherCode / 100 == 7 -> {
                Constant.WEATHER_STATUS[4]
            }
            weatherCode == 800 -> {
                Constant.WEATHER_STATUS[5]
            }
            weatherCode == 801 -> {
                Constant.WEATHER_STATUS[6]
            }
            weatherCode == 803 -> {
                Constant.WEATHER_STATUS[7]
            }
            weatherCode / 100 == 8 -> {
                Constant.WEATHER_STATUS[8]
            }
        }
        return Constant.WEATHER_STATUS[4]
    }



}