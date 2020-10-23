package com.example.lifestyleapp.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "step_table")
class StepDataModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @NotNull
    var step : Int
)