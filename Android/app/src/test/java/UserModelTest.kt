import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserModelTest {
    private lateinit var userDataModelFooBar: UserDataModel
    private lateinit var userModelFooBar: UserModel

    private lateinit var userDataModelWhatsitTooya: UserDataModel
    private lateinit var userModelWhatsitTooya: UserModel

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

        userDataModelWhatsitTooya = UserDataModel(
            firstName = "Whatsit",
            lastName = "Tooya"
        )
        userModelWhatsitTooya = UserModel(userDataModelWhatsitTooya)

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
        assertEquals(userDataModelWhatsitTooya.firstName, "Whatsit")
        assertEquals(userDataModelWhatsitTooya.lastName, "Tooya")
        assertEquals(userDataModelWhatsitTooya.weightChangeGoalPerWeek, 0F)
        assertNull(userDataModelWhatsitTooya.activityLevel)
        assertNull(userDataModelWhatsitTooya.age)
        assertNull(userDataModelWhatsitTooya.city)
        assertNull(userDataModelWhatsitTooya.country)
        assertNull(userDataModelWhatsitTooya.heightInches)
        assertNull(userDataModelWhatsitTooya.weightLbs)
        assertNull(userDataModelWhatsitTooya.profilePicture)
    }

    @Test
    fun calculateBMI() {
        assertEquals(25, userModelFooBar.calculateBMI())
    }

    @Test
    fun calculateNullBMI() {
        assertNull(userModelWhatsitTooya.calculateBMI())
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
        assertNull(userModelWhatsitTooya.calculateBMR())
    }

    @Test
    fun changeGoal() {
        assertEquals(0F, userDataModelFooBar.weightChangeGoalPerWeek)
        userModelFooBar.changeGoal(2F)
        assertEquals(2F, userDataModelFooBar.weightChangeGoalPerWeek)
        assertNotEquals(0F, userDataModelFooBar.weightChangeGoalPerWeek)
    }

    @Test
    fun calculateDailyCaloriesNeededForGoal() {

    }

    @Test
    fun getWeather() {
    }

    @Test
    fun showNearByHikes() {
    }

    @Test
    fun getUserDataModel() {

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
        assertTrue(userDataModelFooBar != userDataModelWhatsitTooya)
        assertTrue(!userDataModelFooBar.equals(userModelFooBar))
        assertTrue(!userModelFooBar.equals(userDataModelFooBar))
        assertTrue(userModelFooBar != userModelWhatsitTooya)
    }

    @Test
    fun notNullTest() {
        assertNotNull(userDataModelFooBar)
        assertNotNull(userModelFooBar)
        assertNotNull(userModelWhatsitTooya)
        assertNotNull(userDataModelWhatsitTooya)
    }

}