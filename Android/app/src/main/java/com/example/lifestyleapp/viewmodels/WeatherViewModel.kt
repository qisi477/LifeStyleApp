package com.example.lifestyleapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lifestyleapp.common.HeavyWorker
import com.example.lifestyleapp.common.Location
import com.example.lifestyleapp.common.Weather
import com.example.lifestyleapp.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class WeatherViewModel : ViewModel() {
    private val _weatherLiveData: MutableLiveData<Weather> by lazy {
        MutableLiveData<Weather>()
    }

    val weatherLiveData: LiveData<Weather> = _weatherLiveData

    private val weatherRepository: WeatherRepository by lazy {
        WeatherRepository()
    }

    fun getWeather(): LiveData<Weather> {
        return weatherRepository.weather
    }

    fun setLocation(location: Location) {
        weatherRepository.loadData(location)
    }

    fun onViewCreated(city: String?, country: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val fetchedWeather =
                HeavyWorker().suspendGetWeather(Location(city = city, country = country))
            _weatherLiveData.postValue(fetchedWeather)
        }
    }
}