package com.example.lifestyleapp.common

import UserDataModel

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
    weightChangeGoalPerWeek = 2.3f
)

data class CalculateData (
    val BMI: Int?,
    val BMR: Int?,
    val daily: Int?
)

val TAG_XX = "XUEFENGX"