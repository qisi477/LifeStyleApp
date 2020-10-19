package com.example.lifestyleapp.common

import com.example.lifestyleapp.repositories.TrailRepository
import com.example.lifestyleapp.repositories.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

//todo get better name for this file
class WeatherRepositoryHolder : WeatherRepository {
    override suspend fun loadWeather(location: Location): Weather? {
        return getWeather(location)
    }
}

class TrailRepositoryHolder : TrailRepository {
    override suspend fun loadTrails(location: Location): List<Trail>? {
        return getTrails(location)
    }
}


interface DispatcherProvider {
    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}

