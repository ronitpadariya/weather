package com.mobiquity.ronit
import com.squareup.moshi.Json


data class Report(
    @Json(name = "clouds")
    val clouds: Clouds,
    @Json(name = "dt")
    val dt: Int,
    @Json(name = "dt_txt")
    val dtTxt: String,
    @Json(name = "main")
    val main: Main,
    @Json(name = "pop")
    val pop: Int,
    @Json(name = "sys")
    val sys: Sys,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "wind")
    val wind: Wind
)

data class Clouds(
    @Json(name = "all")
    val all: Int
)

data class Main(
    @Json(name = "feels_like")
    val feelsLike: Double,
    @Json(name = "grnd_level")
    val grndLevel: Int,
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "sea_level")
    val seaLevel: Int,
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "temp_kf")
    val tempKf: Double,
    @Json(name = "temp_max")
    val tempMax: Double,
    @Json(name = "temp_min")
    val tempMin: Double
)

data class Sys(
    @Json(name = "pod")
    val pod: String
)

data class Weather(
    @Json(name = "description")
    val description: String,
    @Json(name = "icon")
    val icon: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "main")
    val main: String
)

data class Wind(
    @Json(name = "deg")
    val deg: Int,
    @Json(name = "speed")
    val speed: Double
)