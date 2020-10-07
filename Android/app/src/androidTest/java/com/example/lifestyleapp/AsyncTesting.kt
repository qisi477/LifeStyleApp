package com.example.lifestyleapp

import com.example.lifestyleapp.common.DispatcherProvider
import com.example.lifestyleapp.common.HeavyWorker
import com.example.lifestyleapp.common.Location
import kotlinx.coroutines.CoroutineDispatcher
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
 * set up from https://craigrussell.io/2019/11/unit-testing-coroutine-suspend-functions-using-testcoroutinedispatcher/
 */
@ExperimentalCoroutinesApi
class HeavyWorkerTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Test
    fun useRunBlockingTest() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val weather = HeavyWorker(coroutinesTestRule.testDispatcherProvider).loadData(
            Location(
                city = "ogden",
                country = "United States",
                stateCode = "UT"
            )
        )
        Assert.assertNotNull(weather)
        Assert.assertEquals(41.22F, weather!!.coord.latitude)
        Assert.assertEquals(-111.97F, weather.coord.longitude)
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