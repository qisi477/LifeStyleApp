package com.example.lifestyleapp.repositories

import com.example.lifestyleapp.common.Location
import com.example.lifestyleapp.common.Weather

interface WeatherRepository {

    suspend fun loadData(location: Location): Weather?
}