package com.example.lifestyleapp

import com.example.lifestyleapp.common.Location
import com.example.lifestyleapp.common.Weather
import com.example.lifestyleapp.common.WeatherRepositoryHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * set up from this [example](https://craigrussell.io/2019/11/unit-testing-coroutine-suspend-functions-using-testcoroutinedispatcher/)
 */
@ExperimentalCoroutinesApi
class HeavyWorkerTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Test
    fun nullCity() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val weather = WeatherRepositoryHolder().loadWeather(
            Location()
        )
        Assert.assertNull(weather)
    }

    @Test
    fun justCity() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val weather = WeatherRepositoryHolder().loadWeather(
            Location(city = "Ogden")
        )
        notNull(weather)
    }

    @Test
    fun twoWordCity() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val weather = WeatherRepositoryHolder().loadWeather(
            Location(city = "South Weber")
        )
        notNull(weather)
        Assert.assertEquals("South Weber", weather!!.city)
    }

    @Test
    fun threeWordCity() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val weather = WeatherRepositoryHolder().loadWeather(
            Location(city = "Salt Lake City")
        )
        notNull(weather)
        Assert.assertEquals("Salt Lake City", weather!!.city)

    }

    @Test
    fun useRunBlockingTest() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val weather = WeatherRepositoryHolder().loadWeather(
            Location(
                city = "ogden",
                country = "United States",
                stateCode = "UT"
            )
        )

        notNull(weather)

        Assert.assertEquals(41.22F, weather!!.coord.latitude)
        Assert.assertEquals(-111.97F, weather.coord.longitude)

        Assert.assertEquals("Ogden", weather.city)
        Assert.assertEquals("US", weather.sys.countryCode)

    }

    private fun notNull(weather: Weather?) {
        Assert.assertNotNull(weather)
        Assert.assertNotNull(weather!!.coord)
        Assert.assertNotNull(weather.sys)

        Assert.assertNotNull(weather.mainWeather)
        Assert.assertNotNull(weather.mainWeather.atmosphericPressure)
        Assert.assertNotNull(weather.mainWeather.feelsLikeTempKelvin)
        Assert.assertNotNull(weather.mainWeather.humidityPercent)
        Assert.assertNotNull(weather.mainWeather.tempKelvin)
        Assert.assertNotNull(weather.mainWeather.tempMaxKelvin)
        Assert.assertNotNull(weather.mainWeather.tempMinKelvin)

        Assert.assertNotNull(weather.visibility)
        Assert.assertNotNull(weather.wind)
    }
}

@ExperimentalCoroutinesApi
class CoroutineTestRule(
    val testDispatcher: TestCoroutineDispatcher =
        TestCoroutineDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}