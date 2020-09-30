package com.example.lifestyleapp.common

import com.beust.klaxon.JsonArray
import com.beust.klaxon.Klaxon
import java.io.StringReader
import java.net.URL


data class Location(
    var city: String? = null,
    var country: String? = null,
    var stateCode: String? = null,
    var zipCode: String? = null,
    var latitude: Float? = null,
    var longitude: Float? = null,
)

//todo make suspend
fun getTrails(
    location: Location,
    maxDistance: Float = 30F,
    maxResults: Int = 10,
    sort: String = "quality",
    minLength: Int = 0,
    minStars: Float = 0F
): List<Trail>? {
    if (location.latitude == null || location.longitude == null) {
        //todo get lat and lon from phone
        return null
    }
    val apiKey = "200918649-ef46c745c4c243b1fc12581d10410638"
    val url =
        "https://www.hikingproject.com/data/get-trails?" +
                "lat=${location.latitude}" +
                "&lon=${location.longitude}" +
                "&maxDistance=$maxDistance" +
                "&maxResults=$maxResults" +
                "&sort=$sort" +
                "&minLength=$minLength" +
                "&minStars=$minStars" +
                "&key=$apiKey"

    val resultText = URL(url).readText()

    val resultJsonObject = Klaxon().parseJsonObject(reader = StringReader(resultText))
    if (resultJsonObject["success"] != 1) {
        return null
    }
    return Klaxon().parseFromJsonArray<Trail>(map = resultJsonObject["trails"] as JsonArray<*>)
}