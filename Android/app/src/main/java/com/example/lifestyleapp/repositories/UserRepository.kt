package com.example.lifestyleapp.repositories

import androidx.lifecycle.LiveData
import com.example.lifestyleapp.Room.Daos.UserDao
import com.example.lifestyleapp.common.UserDataModel

class UserRepository (private val userDao: UserDao) {
    val allUsers: LiveData<List<UserDataModel>> = userDao.getUserLiveData()

    suspend fun setUser(newUser: UserDataModel) {
        userDao.deleteAll()
        userDao.insert(newUser)
    }
}