package com.example.lifestyleapp.common

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class UserDataModel(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_name")
    var userName: String,
    var age: Int? = null,
    var city: String? = null,
    var country: String? = null,
    @ColumnInfo(name = "height")
    var heightInches: Int? = null,
    @ColumnInfo(name = "weight")
    var weightLbs: Int? = null,
    var sex: String? = null,
    @ColumnInfo(name = "profile_pic_path")
    var profilePicturePath: String? = null,
    @ColumnInfo(name = "active_level")
    var activityLevel: String? = null,
    @ColumnInfo(name = "weight_change")
    var weightChangeGoalPerWeek: Float? = null,
)