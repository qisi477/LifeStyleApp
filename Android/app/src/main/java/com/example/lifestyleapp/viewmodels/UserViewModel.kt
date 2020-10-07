package com.example.lifestyleapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.lifestyleapp.Room.Databases.UserDatabase
import com.example.lifestyleapp.common.UserDataModel
import com.example.lifestyleapp.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository
    val allUsers: LiveData<List<UserDataModel>>

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
        allUsers = userRepository.allUsers
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun setNewUser(user: UserDataModel) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.setUser(user)
    }
}