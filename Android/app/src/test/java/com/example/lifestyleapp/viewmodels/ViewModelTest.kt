package com.example.lifestyleapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lifestyleapp.common.Location
import com.example.lifestyleapp.common.Weather
import com.example.lifestyleapp.repositories.WeatherRepository
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


class ViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    private val fakeWeatherRepository = object : WeatherRepository {
        override suspend fun loadWeather(location: Location): Weather? {
            return null
        }
    }

    @Test
    fun weatherViewModel() {
        val latch = CountDownLatch(1)
        val weatherViewModel = WeatherViewModel()

        weatherViewModel.weatherLiveData.observeForever {
            latch.countDown()
        }
        weatherViewModel.onViewCreated("Ogden", "United States")
        assertTrue(latch.await(2, TimeUnit.SECONDS))
        val weather = weatherViewModel.weatherLiveData.value
        assertNotNull(weather)
        assertEquals("Ogden", weather!!.city)
        assertEquals("US", weather.sys.countryCode)
    }

    @Test
    fun trailViewModel() {
        val latch = CountDownLatch(1)
        val trailViewModel = TrailsViewModel()

        trailViewModel.trailLiveData.observeForever {
            latch.countDown()
        }
        trailViewModel.onViewCreated(
            "Ogden",
            "United States",
            latitude = 41.22F,
            longitude = -111.97F
        )
        assertTrue(latch.await(5, TimeUnit.SECONDS))
        val trails = trailViewModel.trailLiveData.value
        assertNotNull(trails)
        assertEquals(10, trails!!.size)
        trails.forEach {
            assertNotNull(it)
        }
    }

}