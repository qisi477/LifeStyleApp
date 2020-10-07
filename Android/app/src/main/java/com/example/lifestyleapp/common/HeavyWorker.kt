package com.example.lifestyleapp.common

import com.example.lifestyleapp.repositories.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class HeavyWorker(private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()) :
    WeatherRepository {

    override suspend fun loadData(location: Location): Weather? {
        return getWeather(location)
    }
}

interface DispatcherProvider {
    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}

class DefaultDispatcherProvider : DispatcherProvider
