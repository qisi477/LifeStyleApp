package com.example.lifestyleapp.common

import com.beust.klaxon.Json
import com.beust.klaxon.JsonArray
import com.beust.klaxon.Klaxon
import java.io.StringReader
import java.net.URL

//todo not used
data class Trail(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "type")
    val type: String?,
    @Json(name = "summary")
    val summary: String?,
    @Json(name = "difficulty")
    val difficulty: String?,
    @Json(name = "stars")
    val stars: Float?,
    @Json(name = "starVotes")
    val starVotes: Int?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "imgSqSmall")
    val imgSqSmall: String?,
    @Json(name = "imgSmall")
    val imgSmall: String?,
    @Json(name = "imgSmallMed")
    val imgSmallMed: String?,
    @Json(name = "imgMedium")
    val imgMedium: String?,
    @Json(name = "length")
    val length: Float?,
    @Json(name = "ascent")
    val ascent: Int?,
    @Json(name = "descent")
    val descent: Int?,
    @Json(name = "high")
    val high: Int?,
    @Json(name = "low")
    val low: Int?,
    @Json(name = "longitude")
    val longitude: Float?,
    @Json(name = "longitude")
    val latitude: Float?,
    @Json(name = "conditionStatus")
    val conditionStatus: String?,
    @Json(name = "conditionDetails")
    val conditionDetails: String?,
    @Json(name = "conditionDate")
    val conditionDate: String?,
)

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