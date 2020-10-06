package com.example.lifestyleapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.lifestyleapp.common.UserDataModel
import com.example.lifestyleapp.repositories.UserRepository

class UserViewModel : ViewModel() {
    private val userRepository: UserRepository by lazy {
        UserRepository()
    }

    fun getUser(): LiveData<UserDataModel> {
        return userRepository.user
    }

    fun setUser(user: UserDataModel) {
        userRepository.setUser(user)
    }
}