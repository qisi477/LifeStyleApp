package com.example.lifestyleapp.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HeavyWorker(private val dispatchers: DispatcherProvider = DefaultDispatcherProvider()) {
    suspend fun suspendGetWeather(location: Location): Weather? {
        return withContext(dispatchers.io()) {
            getWeather(location)
        }
    }
}

interface DispatcherProvider {
    fun main(): CoroutineDispatcher = Dispatchers.Main
    fun default(): CoroutineDispatcher = Dispatchers.Default
    fun io(): CoroutineDispatcher = Dispatchers.IO
    fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}

class DefaultDispatcherProvider : DispatcherProvider
