package com.example.lifestyleapp.repositories

import androidx.lifecycle.MutableLiveData
import com.example.lifestyleapp.common.UserDataModel

class UserRepository {
    val user: MutableLiveData<UserDataModel> by lazy {
        MutableLiveData<UserDataModel>()
    }

    fun setUser(newUser: UserDataModel) {
        user.value = newUser
        // TODO: ROOM database
    }
}