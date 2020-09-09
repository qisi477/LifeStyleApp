import com.example.lifestyleapp.common.fakeUser
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
            firstName = "Foo",
            lastName = "Bar",
            age = 18,
            city = "Salt Lake City",
            country = "United States of America",
            heightInches = 60,
            weightLbs = 130,
            male = true
        )
        userModelFooBar = UserModel(userDataModelFooBar)

        userDataModelNameOnly = UserDataModel(
            firstName = "Whatsit",
            lastName = "Tooya"
        )
        userModelNameOnly = UserModel(userDataModelNameOnly)

    }

    @Test
    fun createUserTestFooBar() {
        assertEquals("Foo", userDataModelFooBar.firstName)
        assertEquals("Bar", userDataModelFooBar.lastName)
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
        assertEquals(userDataModelNameOnly.firstName, "Whatsit")
        assertEquals(userDataModelNameOnly.lastName, "Tooya")
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
        var fakeUserModel = UserModel(fakeUser)
        assertEquals(19, fakeUserModel.calculateBMI())
        assertEquals(1561, fakeUserModel.calculateBMR())
        assertEquals(961, fakeUserModel.calculateDailyCaloriesNeededForGoal())
    }
}
