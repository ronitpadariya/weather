package com.github.ronit.weather.data
import com.squareup.moshi.Json

data class Forecast(
    @Json(name = "cod")
    var cod: String? = "",
    @Json(name = "message")
    var message: String? = "",
    @Json(name = "cnt")
    var cnt: Int? = 0,
    @Json(name = "city")
    var city: City? = null,
    @Json(name = "list")
    var list: List<Report>? = null,
)

data class City(
    @Json(name = "id")
    var id: Int? = 0,
    @Json(name = "name")
    var name: String? = "",
    @Json(name = "country")
    var country: String? = "",
    @Json(name = "population")
    var population: Int? = 0,
    @Json(name = "timezone")
    var timezone: Int? = 0,
    @Json(name = "sunsrise")
    var sunrise: Long? = 0,
    @Json(name = "sunset")
    var sunset: Long? = 0,
    @Json(name = "coord")
    var coord: Coord? = null
)

data class Report(
    @Json(name = "base")
    var base: String? = null,
    @Json(name = "clouds")
    var clouds: Clouds? = null,
    @Json(name = "cod")
    var cod: Int? = null,
    @Json(name = "coord")
    var coord: Coord? = null,
    @Json(name = "dt")
    var dt: Long? = null,
    @Json(name = "main")
    var main: Main? = null,
    @Json(name = "name")
    var name: String? = null,
    @Json(name = "sys")
    var sys: Sys? = null,
    @Json(name = "timezone")
    var timezone: Int? = null,
    @Json(name = "visibility")
    var visibility: Int? = null,
    @Json(name = "weather")
    var weather: List<Weather>? = null,
    @Json(name = "wind")
    var wind: Wind? = null
)

data class Clouds(
    @Json(name = "all")
    var all: Int? = null
)

data class Coord(
    @Json(name = "lat")
    var lat: Double? = null,
    @Json(name = "lon")
    var lon: Double? = null
)

data class Main(
    @Json(name = "feels_like")
    var feelsLike: Double? = null,
    @Json(name = "grnd_level")
    var grndLevel: Int? = null,
    @Json(name = "humidity")
    var humidity: Int? = null,
    @Json(name = "pressure")
    var pressure: Int? = null,
    @Json(name = "sea_level")
    var seaLevel: Int? = null,
    @Json(name = "temp")
    var temp: Double? = 0.00,
    @Json(name = "temp_max")
    var tempMax: Double? = null,
    @Json(name = "temp_min")
    var tempMin: Double? = null
)

data class Sys(
    @Json(name = "country")
    var country: String? = null,
    @Json(name = "sunrise")
    var sunrise: Long? = null,
    @Json(name = "sunset")
    var sunset: Long? = null
)

data class Weather(
    @Json(name = "description")
    var description: String? = null,
    @Json(name = "icon")
    var icon: String? = null,
    @Json(name = "id")
    var id: Int? = null,
    @Json(name = "main")
    var main: String? = null
)

data class Wind(
    @Json(name = "deg")
    var deg: Int? = null,
    @Json(name = "gust")
    var gust: Double? = null,
    @Json(name = "speed")
    var speed: Double? = null
)