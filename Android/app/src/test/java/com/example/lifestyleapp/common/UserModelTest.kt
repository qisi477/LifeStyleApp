package com.example.lifestyleapp.common

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserModelTest {
    private lateinit var userDataModelFooBar: UserDataModel
    private lateinit var userModelFooBar: UserModel

    private lateinit var userDataModelNameOnly: UserDataModel
    private lateinit var userModelNameOnly: UserModel

    @Before
    fun setUp() {
        userDataModelFooBar = UserDataModel(
            userName = "FooBar",
            age = 18,
            city = "Salt Lake City",
            country = "United States of America",
            heightInches = 60,
            weightLbs = 130,
            male = true
        )
        userModelFooBar = UserModel(userDataModelFooBar)

        userDataModelNameOnly = UserDataModel(
            userName = "Whatsit"
        )
        userModelNameOnly = UserModel(userDataModelNameOnly)

    }

    @Test
    fun createUserTestFooBar() {
        assertEquals("FooBar", userDataModelFooBar.userName)
        assertEquals(18, userDataModelFooBar.age)
        assertEquals("Salt Lake City", userDataModelFooBar.city)
        assertEquals("United States of America", userDataModelFooBar.country)
        assertEquals(60, userDataModelFooBar.heightInches)
        assertEquals(130, userDataModelFooBar.weightLbs)
        assertEquals(true, userDataModelFooBar.male)
        assertNull(userDataModelFooBar.profilePicture)
        assertNull(userDataModelFooBar.activityLevel)
    }

    @Test
    fun createNameOnlyUser() {
        assertEquals(userDataModelNameOnly.userName, "Whatsit")
        assertNull(userDataModelNameOnly.weightChangeGoalPerWeek)
        assertNull(userDataModelNameOnly.activityLevel)
        assertNull(userDataModelNameOnly.age)
        assertNull(userDataModelNameOnly.city)
        assertNull(userDataModelNameOnly.country)
        assertNull(userDataModelNameOnly.heightInches)
        assertNull(userDataModelNameOnly.weightLbs)
        assertNull(userDataModelNameOnly.profilePicture)
    }

    @Test
    fun calculateBMI() {
        assertEquals(25, userModelFooBar.calculateBMI())

    }

    @Test
    fun calculateNullBMI() {
        assertNull(userModelNameOnly.calculateBMI())
    }

    @Test
    fun calculateBMR() {
        assertEquals(1516, userModelFooBar.calculateBMR())
        userDataModelFooBar.male = false
        assertEquals(1418, userModelFooBar.calculateBMR())
        userDataModelFooBar.male = true
        assertEquals(1516, userModelFooBar.calculateBMR())
    }

    @Test
    fun calculateNullBMR() {
        assertNull(userModelNameOnly.calculateBMR())
        assertNotNull(userModelFooBar.calculateBMR())
    }

    @Test
    fun changeGoal() {
        assertNull(userDataModelFooBar.weightChangeGoalPerWeek)
        userModelFooBar.changeGoal(2F)
        assertEquals(2F, userDataModelFooBar.weightChangeGoalPerWeek)
        assertNotEquals(0F, userDataModelFooBar.weightChangeGoalPerWeek)
        assertNotNull(userDataModelFooBar.weightChangeGoalPerWeek)
    }

    @Test
    fun calculateDailyCaloriesNeededForGoal() {
        assertEquals(
            userModelFooBar.calculateBMR(),
            userModelFooBar.calculateDailyCaloriesNeededForGoal()
        )
        assertEquals(
            2000,
            userModelNameOnly.calculateDailyCaloriesNeededForGoal()
        )
    }

    @Test
    fun setUserDataModelAge() {
        assertEquals(18, userDataModelFooBar.age)
        userDataModelFooBar.age = userDataModelFooBar.age?.plus(1)
        assertEquals(19, userDataModelFooBar.age)
        userDataModelFooBar.age = userDataModelFooBar.age?.minus(1)
        assertEquals(18, userDataModelFooBar.age)

    }

    @Test
    fun equalsItsSelfTest() {
        assertTrue(userDataModelFooBar == userDataModelFooBar)
        assertTrue(userDataModelFooBar != userDataModelNameOnly)
        assertTrue(!userDataModelFooBar.equals(userModelFooBar))
        assertTrue(!userModelFooBar.equals(userDataModelFooBar))
        assertTrue(userModelFooBar != userModelNameOnly)
    }

    @Test
    fun notNullTest() {
        assertNotNull(userDataModelFooBar)
        assertNotNull(userModelFooBar)
        assertNotNull(userModelNameOnly)
        assertNotNull(userDataModelNameOnly)
    }

    @Test
    fun fakeUserTest() {
        val fakeUserModel = UserModel(fakeUser)
        assertEquals(19, fakeUserModel.calculateBMI())
        assertEquals(1561, fakeUserModel.calculateBMR())
        assertEquals(1461, fakeUserModel.calculateDailyCaloriesNeededForGoal())
    }


    @Test
    fun parseExampleWeather() {
        val exampleApiResponse = "{\n" +
                "  \"coord\": {\"lon\": -122.08,\"lat\": 37.39},\n" +
                "  \"weather\": [\n" +
                "    {\n" +
                "      \"id\": 800,\n" +
                "      \"main\": \"Clear\",\n" +
                "      \"description\": \"clear sky\",\n" +
                "      \"icon\": \"01d\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"base\": \"stations\",\n" +
                "  \"main\": {\n" +
                "    \"temp\": 281.52,\n" +
                "    \"feels_like\": 278.99,\n" +
                "    \"temp_min\": 280.15,\n" +
                "    \"temp_max\": 283.71,\n" +
                "    \"pressure\": 1016,\n" +
                "    \"humidity\": 93\n" +
                "  },\n" +
                "  \"visibility\": 16093,\n" +
                "  \"wind\": {\n" +
                "    \"speed\": 1.5,\n" +
                "    \"deg\": 350\n" +
                "  },\n" +
                "  \"clouds\": {\n" +
                "    \"all\": 1\n" +
                "  },\n" +
                "  \"dt\": 1560350645,\n" +
                "  \"sys\": {\n" +
                "    \"type\": 1,\n" +
                "    \"id\": 5122,\n" +
                "    \"message\": 0.0139,\n" +
                "    \"country\": \"US\",\n" +
                "    \"sunrise\": 1560343627,\n" +
                "    \"sunset\": 1560396563\n" +
                "  },\n" +
                "  \"timezone\": -25200,\n" +
                "  \"id\": 420006353,\n" +
                "  \"name\": \"Mountain View\",\n" +
                "  \"cod\": 200\n" +
                "}"
        val weather = jsonTextToWeather(exampleApiResponse)
        assertEquals(-122.08F, weather!!.coord.longitude)
        assertEquals(37.39F, weather.coord.latitude)
        assertEquals(
            MainWeather(
                tempKelvin = 281.52F,
                feelsLikeTempKelvin = 278.99F,
                tempMinKelvin = 280.15F,
                tempMaxKelvin = 283.71F,
                pressure = 1016,
                humidity = 93
            ), weather.mainWeather
        )
        assertEquals(Wind(speed = 1.5F, degreesDirection = 350F), weather.wind)
    }


}
