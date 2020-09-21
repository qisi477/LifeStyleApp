package com.example.lifestyleapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.lifestyleapp.common.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {
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
        val trails = getTrails(location)
        assertNotNull(trails)
    }
}

/**
 * set up from https://craigrussell.io/2019/11/unit-testing-coroutine-suspend-functions-using-testcoroutinedispatcher/
 */
@ExperimentalCoroutinesApi
class HeavyWorkerTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Test
    fun useRunBlockingTest() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val weather = HeavyWorker(coroutinesTestRule.testDispatcherProvider).suspendGetWeather(
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
}

@ExperimentalCoroutinesApi
class CoroutineTestRule(
    val testDispatcher: TestCoroutineDispatcher =
        TestCoroutineDispatcher()
) : TestWatcher() {

    val testDispatcherProvider = object : DispatcherProvider {
        override fun default(): CoroutineDispatcher = testDispatcher
        override fun io(): CoroutineDispatcher = testDispatcher
        override fun main(): CoroutineDispatcher = testDispatcher
        override fun unconfined(): CoroutineDispatcher = testDispatcher
    }

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
