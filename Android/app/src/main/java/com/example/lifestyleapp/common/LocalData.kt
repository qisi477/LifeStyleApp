package com.example.lifestyleapp.common

import UserDataModel
import android.app.Activity
import android.content.Context
import com.google.gson.Gson

class LocalData (val activity: Activity?) {
    private val DATA_LABEL = "LocalData"
    private val USER_LABEL = "CurrentUser"

    val sharedPreferences = activity?.getSharedPreferences(DATA_LABEL, Context.MODE_PRIVATE)
    // val sharedPreferences = activity.getSharedPreferences(DATA_LABEL, Context.MODE_PRIVATE)

    fun saveUser(usr: UserDataModel) {
        val editor = sharedPreferences?.edit() ?: return
        val gson = Gson()
        editor.putString(USER_LABEL, gson.toJson(usr))
        editor.apply()
    }

    fun getUser() : UserDataModel? {
        val usr = sharedPreferences?.getString(USER_LABEL, null) ?: return null
        val gson = Gson()
        return gson.fromJson(usr, UserDataModel::class.java)
    }
}