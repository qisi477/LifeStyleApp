package com.example.lifestyleapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.lifestyleapp.common.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AndroidContextTesting {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.lifestyleapp", appContext.packageName)
    }

    @Test
    fun getWeatherTest() {
        val weather = getWeather(
            Location(
                city = "ogden",
                country = "United States",
                stateCode = "UT"
            )
        )
        assertNotNull(weather)
        assertEquals(41.22F, weather!!.coord.latitude)
        assertEquals(-111.97F, weather.coord.longitude)
    }

    @Test
    fun getWeatherFakeUser() {
        val weather = getWeather(
            Location(
                city = fakeUser.city,
                country = fakeUser.country
            )
        )
        assertNotNull(weather)
    }

    @Test
    fun getWeatherFakeUser2() {
        val weather = getWeather(
            Location(
                city = fakeUser2.city,
                country = fakeUser2.country
            )
        )
        assertNotNull(weather)
    }

    @Test
    fun getTrailsTest() {
        val location = Location(
            city = "Ogden",
            country = "US",
            stateCode = "UT",
            latitude = 41.22F,
            longitude = -111.97F
        )
        val trails = getTrails(location, maxResults = 10)
        assertNotNull(trails)
        assertEquals(10, trails!!.size)
        trails.forEach {
            assertNotNull(it)
        }
    }
}


