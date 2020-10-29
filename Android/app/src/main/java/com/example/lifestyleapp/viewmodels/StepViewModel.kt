package com.example.lifestyleapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lifestyleapp.Room.Databases.StepDatabase
import com.example.lifestyleapp.common.StepDataModel
import com.example.lifestyleapp.repositories.StepRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StepViewModel(application: Application) : AndroidViewModel(application) {
    private val stepRepository: StepRepository
    val steps: LiveData<List<StepDataModel>>

    init {
        val stepDao = StepDatabase.getDatabase(application).stepDao()
        stepRepository = StepRepository(stepDao)
        steps = stepRepository.steps
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertStep(step: StepDataModel) = viewModelScope.launch(Dispatchers.IO) {
        stepRepository.insertStep(step)
    }

}