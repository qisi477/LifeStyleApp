package com.example.lifestyleapp.common

val fakeUser = UserDataModel(
    firstName = "Varun",
    lastName = "Shankar",
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
    firstName = "SomeOne",
    lastName = "Shankar",
    age = 30,
    city = "Salt Lake City",
    country = "US",
    heightInches = 70,
    weightLbs = 130,
    male = true,
    activityLevel = "3",
    weightChangeGoalPerWeek = -1.2f
)

data class CalculateData (
    val BMI: Int?,
    val BMR: Int?,
    val daily: Int?
)

val TAG_XX = "XUEFENGX"

