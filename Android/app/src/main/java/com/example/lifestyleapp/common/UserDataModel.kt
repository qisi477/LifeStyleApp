package com.example.lifestyleapp.common

data class UserDataModel(
    var userName: String,
    var age: Int? = null,
    var city: String? = null,
    var country: String? = null,
    var heightInches: Int? = null,
    var weightLbs: Int? = null,
    var sex: String? = null,
    var profilePicturePath: String? = null,
    var activityLevel: String? = null,
    var weightChangeGoalPerWeek: Float? = null,
)