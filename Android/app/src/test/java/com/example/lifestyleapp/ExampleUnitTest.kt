package com.example.lifestyleapp

import User
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun createUserTest() {
        val user = User(
            firstName = "Foo",
            lastName = "Bar",
            age = 18,
            city = "Salt Lake City",
            country = "United States of America",
            heightInches = 60,
            weightLbs = 170,
            male = true
        )
        assertEquals("Foo", user.firstName)
        assertEquals("Bar", user.lastName)
        assertEquals(18, user.age)
        assertEquals("Salt Lake City", user.city)
        assertEquals("United States of America", user.country)
        assertEquals(60, user.heightInches)
        assertEquals(170, user.weightLbs)
        assertEquals(true, user.male)
        assertNotEquals(true, user.female)
        assertNull(user.profilePicture)
        assertNull(user.activityLevel)

    }
    //TODO test everything else

}