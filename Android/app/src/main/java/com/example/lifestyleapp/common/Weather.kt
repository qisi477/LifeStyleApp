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
    val mainWeather: MainWeather,
    val visibility: Int,
    val wind: Wind,
)

data class Coord(
    val lon: Float,
    val lat: Float,
)

data class MainWeather(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int,
)

data class Wind(
    val speed: Float,
    val deg: Float,
)

fun getWeather(location: Location): Weather? {
    val apiKey = "a742f92606870e1ee06b22a9502b644d"

    var url = "https://api.openweathermap.org/data/2.5/weather?q="
    if (location.city != null) {
        url += location.city
    }
    if (location.stateCode != null) {
        url += ",${location.stateCode}"
    }
    if (location.countryCode != null) {
        url += ",${location.countryCode}"
    }

    url += "&appid=$apiKey"


// Instantiate the cache
    val cacheDir = File("res/cache")
    val cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
    val network = BasicNetwork(HurlStack())

// Instantiate the RequestQueue with the cache and network. Start the queue.
    val requestQueue = RequestQueue(cache, network).apply {
        start()
    }

    lateinit var returnText: String
    // Formulate the request and handle the response.
    val stringRequest = StringRequest(
        Request.Method.GET, url,
        { response: String ->
            returnText = response
        },
    ) {
        // Handle error
        returnText = "ERROR: %s".format(it.toString())
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