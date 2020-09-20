package com.example.lifestyleapp.common

import com.beust.klaxon.Json

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