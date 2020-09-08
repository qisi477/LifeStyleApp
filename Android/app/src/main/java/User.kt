import android.graphics.Picture
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt

class User constructor(
    var firstName: String,
    var lastName: String,
    var age: Int,
    var city: String? = null,
    var country: String? = null,
    var heightInches: Int,
    var weightLbs: Int,
    var male: Boolean = false,
    var female: Boolean = false,
    var profilePicture: Picture? = null,
    var activityLevel: String? = null,
    var weightChangeGoalPerWeek: Float = 0F
) {

    fun calculateBMI(): Int {
        //imperial BMI = 703 * weight/height^2
        //metric BMI = weight/height^2
        return (703 * weightLbs / heightInches.toDouble().pow(2)).roundToInt()
    }

    /**
     * @return Basal Metabolic Rate, the basic number of calories I need to survive
     */
    fun calculateBMR(): Int {
        //Revised Harris-Benedict Equation from https://www.calculator.net/bmr-calculator.html?ctype=standard&cage=25&csex=m&cheightfeet=5&cheightinch=10&cpound=160&cheightmeter=180&ckg=60&cmop=0&coutunit=c&cformula=m&cfatpct=20
        return if (male) {
            (13.397 * weightLbs + 4.799 * heightInches - 5.677 * age + 88.362).roundToInt()
        } else { //Female formula
            (9.247 * weightLbs + 3.098 * heightInches - 4.330 * age + 447.593).roundToInt()
        }
    }

    fun changeGoal(weightChangePerWeek: Float) {
        weightChangeGoalPerWeek = weightChangePerWeek
        if (abs(weightChangePerWeek) > 2) {
            //todo warn
        }
    }

    fun calculateDailyCaloriesNeededForGoal(): Int {
        val caloriesPerPound = 3500
        return (calculateBMR() - weightChangeGoalPerWeek * caloriesPerPound / 7 + getCaloriesBurnedPerDay()).roundToInt()
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