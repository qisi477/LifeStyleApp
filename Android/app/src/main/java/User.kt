import android.graphics.Picture
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt

data class UserData(
    var firstName: String,
    var lastName: String,
    var age: Int? = null,
    var city: String? = null,
    var country: String? = null,
    var heightInches: Int? = null,
    var weightLbs: Int? = null,
    var male: Boolean? = false,
    var profilePicture: Picture? = null,
    var activityLevel: String? = null,
    var weightChangeGoalPerWeek: Float = 0F,
)

class User(var userData: UserData) {

    /**
     * imperial BMI = 703 * weight/height^2
     * metric BMI = weight/height^2
     */
    fun calculateBMI(): Int? {
        return if (userData.weightLbs == null || userData.heightInches == null) {
            null
        } else {
            (703 * userData.weightLbs!! / userData.heightInches!!.toDouble().pow(2)).roundToInt()
        }
    }

    /**
     * @return Basal Metabolic Rate, the basic number of calories one needs to survive
     */
    fun calculateBMR(): Int? {
        //Revised Harris-Benedict Equation from https://www.calculator.net/bmr-calculator.html?ctype=standard&cage=25&csex=m&cheightfeet=5&cheightinch=10&cpound=160&cheightmeter=180&ckg=60&cmop=0&coutunit=c&cformula=m&cfatpct=20
        return if (userData.male == null || userData.weightLbs == null || userData.heightInches == null || userData.age == null) {
            null
        } else if (userData.male!!) {
            (13.397 * userData.weightLbs!! + 4.799 * userData.heightInches!! - 5.677 * userData.age!! + 88.362).roundToInt()
        } else { //Female formula
            (9.247 * userData.weightLbs!! + 3.098 * userData.heightInches!! - 4.330 * userData.age!! + 447.593).roundToInt()
        }
    }

    fun changeGoal(weightChangePerWeek: Float) {
        userData.weightChangeGoalPerWeek = weightChangePerWeek
        if (abs(weightChangePerWeek) > 2) {
            //todo warn
        }
    }

    fun calculateDailyCaloriesNeededForGoal(): Int? {
        val caloriesPerPound = 3500
        return ((calculateBMR()?.minus(userData.weightChangeGoalPerWeek * caloriesPerPound / 7)
            ?: return null) + getCaloriesBurnedPerDay()).roundToInt()
    }

    /**
     * based on activity Level
     */
    private fun getCaloriesBurnedPerDay(): Float {
        TODO("getCaloriesBurnedPerDay Not yet implemented")
    }

    fun getWeather() {
        TODO("google weather api call based on city and country")
    }

    fun showNearByHikes() {
        TODO("all trails (or something) api call based on location")
    }


}