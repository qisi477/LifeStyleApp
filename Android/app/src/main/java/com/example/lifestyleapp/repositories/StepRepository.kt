package com.example.lifestyleapp.repositories

import androidx.lifecycle.LiveData
import com.example.lifestyleapp.Room.Daos.StepDao
import com.example.lifestyleapp.common.StepDataModel


class StepRepository(private val stepDao: StepDao) {
    val steps: LiveData<List<StepDataModel>> = stepDao.getStepsData()

    suspend fun insertStep(stepDataModel: StepDataModel) {
        stepDao.insert(stepDataModel)
    }

}