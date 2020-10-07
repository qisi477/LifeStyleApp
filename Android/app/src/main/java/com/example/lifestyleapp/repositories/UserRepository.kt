package com.example.lifestyleapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.lifestyleapp.Room.Daos.UserDao
import com.example.lifestyleapp.common.TAG_XX
import com.example.lifestyleapp.common.UserDataModel

class UserRepository (private val userDao: UserDao) {
    val allUsers: LiveData<List<UserDataModel>> = userDao.getUserLiveData()

    suspend fun getUser(): List<UserDataModel> {
        return userDao.getUsers()
    }

    suspend fun setUser(newUser: UserDataModel) {
        //Log.d(TAG_XX, userDao.getUsers()[0].userName)
        if (userDao.getUsers().isNotEmpty()) {
            userDao.deleteAll()
             //Log.d(TAG_XX, "Already delete all users")
        }
        userDao.insert(newUser)
        //Log.d(TAG_XX, userDao.getUsers()[0].userName)
    }
}