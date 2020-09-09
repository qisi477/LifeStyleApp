import kotlin.math.pow
import kotlin.math.roundToInt

class UserModel(var userDataModel: UserDataModel) {

    /**
     * imperial BMI = 703 * weight/height^2
     * metric BMI = weight/height^2
     */
    fun calculateBMI(): Int? {
        return if (userDataModel.weightLbs == null || userDataModel.heightInches == null) {
            null
        } else {
            (703 * userDataModel.weightLbs!! / userDataModel.heightInches!!.toDouble()
                .pow(2)).roundToInt()
        }
    }

    /**
     * @return Basal Metabolic Rate, the basic number of calories one needs to survive
     */
    fun calculateBMR(): Int? {
        //formula from https://www.bmi-calculator.net/bmr-calculator/bmr-formula.php
        return if (
            userDataModel.male == null ||
            userDataModel.weightLbs == null ||
            userDataModel.heightInches == null ||
            userDataModel.age == null
        ) {
            null
        } else if (userDataModel.male!!) {
            (66 + (6.23 * userDataModel.weightLbs!!) + (12.7 * userDataModel.heightInches!!) - (6.8 * userDataModel.age!!)).roundToInt()
        } else { //Female formula
            (655 + (4.35 * userDataModel.weightLbs!!) + (4.7 * userDataModel.heightInches!!) - (4.7 * userDataModel.age!!)).roundToInt()
        }
    }

    fun changeGoal(weightChangePerWeek: Float) {
        userDataModel.weightChangeGoalPerWeek = weightChangePerWeek
    }

    fun calculateDailyCaloriesNeededForGoal(): Int? {
        val caloriesPerPound = 3500
        return ((calculateBMR()?.minus(userDataModel.weightChangeGoalPerWeek * caloriesPerPound / 7)
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