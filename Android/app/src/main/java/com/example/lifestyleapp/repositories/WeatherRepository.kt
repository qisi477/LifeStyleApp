package com.example.lifestyleapp.repositories

import androidx.lifecycle.MutableLiveData
import com.example.lifestyleapp.common.Location
import com.example.lifestyleapp.common.Weather

class WeatherRepository {
    val weather: MutableLiveData<Weather> by lazy {
        MutableLiveData<Weather>()
    }

    fun loadData(location: Location) {
        // TODO: coroutine to fetch open weather API
    }
}