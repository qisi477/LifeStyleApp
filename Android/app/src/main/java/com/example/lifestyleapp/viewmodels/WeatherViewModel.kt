package com.example.lifestyleapp.viewmodels

import androidx.lifecycle.LiveData
import com.example.lifestyleapp.common.Location
import com.example.lifestyleapp.common.Weather
import com.example.lifestyleapp.repositories.WeatherRepository

class WeatherViewModel {
    private val weatherRepository: WeatherRepository by lazy {
        WeatherRepository()
    }

    fun getWeather(): LiveData<Weather> {
        return weatherRepository.weather
    }

    fun setLocation(location: Location) {
        weatherRepository.loadData(location)
    }
}