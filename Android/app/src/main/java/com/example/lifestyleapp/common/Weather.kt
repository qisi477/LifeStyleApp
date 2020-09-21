package com.example.lifestyleapp.common

import android.util.Log
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
) {
    fun getTempFahrenheit(): Float {
        return kelvinToFahrenheit(tempKelvin)
    }

    fun getTempCelsius(): Float {
        return kelvinToCelsius(tempKelvin)
    }

    fun getFeelsLikeTempFahrenheit(): Float {
        return kelvinToFahrenheit(feelsLikeTempKelvin)
    }

    fun getFeelsLikeTempCelsius(): Float {
        return kelvinToCelsius(feelsLikeTempKelvin)
    }

    private fun kelvinToFahrenheit(kelvinTemp: Float): Float {
        return (kelvinTemp - 273.15F) * (9F / 5F) + 32F
    }

    private fun kelvinToCelsius(kelvinTemp: Float): Float {
        return (kelvinTemp - 273.15F)
    }
}

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
    val url = buildURL(location)
    Log.d(TAG_WEATHER, url)
    val result: String = URL(url).readText()
    Log.d(TAG_WEATHER, result)
    return jsonTextToWeather(result)
}

class HeavyWorker(private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()) {
    suspend fun suspendGetWeather(location: Location): Weather? {
        return withContext(dispatchers.io()) {
            getWeather(location)
        }
    }
}


interface DispatcherProvider {

    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined

}

class DefaultDispatcherProvider : DispatcherProvider

/*
docs https://openweathermap.org/current
example query
https://api.openweathermap.org/data/2.5/weather?q=Ogden,ut,usa&appid=a742f92606870e1ee06b22a9502b644d
 */
private fun buildURL(location: Location): String {
    var url = "https://api.openweathermap.org/data/2.5/weather?q="
    if (!location.city.isNullOrEmpty()) url += location.city!!.trim().replace(" ", "%20")
    if (!location.country.isNullOrEmpty()) {
        url += ",${
            location.country!!.trim().replace(" ", "%20")
        }"
    }
    val apiKey = "a742f92606870e1ee06b22a9502b644d"
    url += "&appid=$apiKey"
    return url
}

fun jsonTextToWeather(jsonText: String): Weather? {
    if (jsonText.startsWith("ERROR: ")) {
        return null
    }
    return Klaxon().parse<Weather>(reader = StringReader(jsonText))
}