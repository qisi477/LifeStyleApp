package com.example.lifestyleapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lifestyleapp.common.Location
import com.example.lifestyleapp.common.Weather
import com.example.lifestyleapp.repositories.WeatherRepository
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


class WeatherViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    private val fakeWeatherRepository = object : WeatherRepository {
        override suspend fun loadData(location: Location): Weather? {
            return null
        }

    }

    @Test
    fun firstTest() {
        val latch = CountDownLatch(1)
        val weatherViewModel = WeatherViewModel()

        weatherViewModel.weatherLiveData.observeForever {
            latch.countDown()
        }
        weatherViewModel.onViewCreated("Ogden", "United States")
        assertTrue(latch.await(2, TimeUnit.SECONDS))
    }

}