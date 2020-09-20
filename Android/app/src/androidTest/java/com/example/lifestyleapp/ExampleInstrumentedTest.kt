package com.example.lifestyleapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.lifestyleapp.common.Location
import com.example.lifestyleapp.common.getTrails
import com.example.lifestyleapp.common.getWeather
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
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.lifestyleapp", appContext.packageName)
    }

    @Test
    fun getWeatherTest() { //relies on context
        val location = Location(
            city = "Ogden",
            countryCode = "US",
            stateCode = "UT"
        )
        val weather = getWeather(location)
        assertNotNull(weather)
        assertEquals(41.22F, weather!!.coord.latitude)
        assertEquals(-111.97F, weather.coord.longitude)
        getTrails(location)
    }

    @Test
    fun getTrailsTest() {
        val location = Location(
            city = "Ogden",
            countryCode = "US",
            stateCode = "UT",
            latitude = 41.22F,
            longitude = -111.97F
        )
        val trails = getTrails(location)
        assertNotNull(trails)
    }
}