package com.example.lifestyleapp.common

import kotlin.math.pow
import kotlin.math.roundToInt

class UserModel(private var userDataModel: UserDataModel) {

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
        var bmr = calculateBMR()
        if (bmr == null) {
            bmr = 2000
        }

        var goal = userDataModel.weightChangeGoalPerWeek
        if (goal == null) {
            goal = 0F
        }

        var caloriesBurnedPerDay = getCaloriesBurnedPerDay()
        if (caloriesBurnedPerDay == null) {
            caloriesBurnedPerDay = 0F
        }

        return (bmr + (goal / 7 * caloriesPerPound) + caloriesBurnedPerDay).roundToInt()
    }

    /**
     * very rough estimate of excess calories burned per day through exercise
     */
    private fun getCaloriesBurnedPerDay(): Float? {
        return when (userDataModel.activityLevel) {
            //todo make accurate and robust
            "Sedentary" -> 0f
            "Active" -> 500f
            null -> null
            else -> null
        }
    }

}