package com.example.lifestyleapp.common


data class Weather(
    var tempature: Int?,
    var windSpeed: Int?,
    var precipitation: Int?,
    var Humidity: Int?,
) {
    fun getWeather() {
        //todo
        //api call based on location
        //store and set relevant parameters
        //return Weather
    }
}