package com.example.lifestyleapp.common

enum class Signals {
    SUMMARY,
    LOGOUT,
    HIKE,
    WEATHER,
    SETTING
}


val fakeUser = UserDataModel(
    userName = "Varun",
    age = 30,
    city = "Salt Lake City",
    country = "US",
    heightInches = 70,
    weightLbs = 130,
    male = true,
    activityLevel = "3",
    weightChangeGoalPerWeek = -1.2f
)

val fakeUser2 = UserDataModel(
    userName = "SomeOne",
    age = 30,
    city = "Salt Lake City",
    country = "US",
    heightInches = 70,
    weightLbs = 130,
    male = true,
    activityLevel = "3",
    weightChangeGoalPerWeek = 0f
)

data class CalculateData(
    val BMI: Int?,
    val BMR: Int?,
    val daily: Int?
)

val TAG_XX = "XUEFENGX"
val TAG_WEATHER = "WEATHER"
val TAG_CH = "CHADHURST"

