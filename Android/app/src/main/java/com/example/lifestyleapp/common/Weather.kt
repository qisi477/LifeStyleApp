package com.example.lifestyleapp.common

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.StringRequest
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import java.io.File
import java.io.StringReader

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

fun getWeather(location: Location): Weather? {
    val apiKey = "a742f92606870e1ee06b22a9502b644d"

    var url = "https://api.openweathermap.org/data/2.5/weather?q="
    if (!location.city.isNullOrEmpty()) url += location.city
    if (!location.stateCode.isNullOrEmpty()) url += ",${location.stateCode}"
    if (!location.countryCode.isNullOrEmpty()) url += ",${location.countryCode}"
    url += "&appid=$apiKey"

// Instantiate the cache
    val cacheDir = File("res/cache") //fixme this probably doesn't work
    val cache = DiskBasedCache(cacheDir, 1024 * 1024) // //todo 1MB cap, could be much less

// Set up the network to use HttpURLConnection as the HTTP client.
    val network = BasicNetwork(HurlStack())

// Instantiate the RequestQueue with the cache and network. Start the queue.
    val requestQueue = RequestQueue(cache, network).apply {
        start()
    }

    lateinit var returnText: String
    // Formulate the request and handle the response.
    val stringRequest = StringRequest(
        Request.Method.GET,
        url,
        { response: String ->
            returnText = response
        },
    ) {
        returnText = "ERROR: %s".format("$it")
    }
    requestQueue.add(stringRequest)


    return jsonTextToWeather(returnText) //fixme lateinit property returnText has not been initialized
}

fun jsonTextToWeather(jsonText: String): Weather? {
    if (jsonText.startsWith("ERROR: ")) {
        return null
    }
    return Klaxon().parse<Weather>(reader = StringReader(jsonText))
}