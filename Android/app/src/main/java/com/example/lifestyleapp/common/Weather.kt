package com.example.lifestyleapp.common

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import java.io.StringReader
import java.net.URL

data class Weather(
    @Json(name = "coord")
    val coord: Coord,
    @Json(name = "main")
    val mainWeather: MainWeather, //this is how the api sends it
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "wind")
    val wind: Wind,
)

data class Coord(
    @Json(name = "lon")
    val longitude: Float,
    @Json(name = "lat")
    val latitude: Float,
)

data class MainWeather(
    @Json(name = "temp")
    val tempKelvin: Float,
    @Json(name = "feels_like")
    val feelsLikeTempKelvin: Float,
    @Json(name = "temp_min")
    val tempMinKelvin: Float,
    @Json(name = "temp_max")
    val tempMaxKelvin: Float,
    @Json(name = "pressure")
    val pressure: Int, //todo get units
    @Json(name = "humidity")
    val humidity: Int, //todo get units
)

data class Wind(
    @Json(name = "speed")
    val speed: Float, //todo get units
    @Json(name = "deg")
    val degreesDirection: Float,
)

/**
 * provided by openweathermap, Current weather and forecast: Free plan
 * Calls per minute: 60
 */
fun getWeather(location: Location): Weather? {
    val apiKey = "a742f92606870e1ee06b22a9502b644d"

    var url = "https://api.openweathermap.org/data/2.5/weather?q="
    if (!location.city.isNullOrEmpty()) url += location.city
    if (!location.stateCode.isNullOrEmpty()) url += ",${location.stateCode}"
    if (!location.countryCode.isNullOrEmpty()) url += ",${location.countryCode}"
    url += "&appid=$apiKey"

    val result = URL(url).readText()
    return jsonTextToWeather(result)
}

fun jsonTextToWeather(jsonText: String): Weather? {
    if (jsonText.startsWith("ERROR: ")) {
        return null
    }
    return Klaxon().parse<Weather>(reader = StringReader(jsonText))
}