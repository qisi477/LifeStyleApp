package com.example.lifestyleapp.common

import android.app.Activity
import android.content.Context
import com.google.gson.Gson

class LocalData(activity: Activity?) {
    private val dataLabel: String = "LocalData"
    private val userLabel: String = "CurrentUser"

    private val sharedPreferences = activity?.getSharedPreferences(dataLabel, Context.MODE_PRIVATE)
    // val sharedPreferences = activity.getSharedPreferences(DATA_LABEL, Context.MODE_PRIVATE)

    fun saveUser(usr: UserDataModel) {
        val editor = sharedPreferences?.edit() ?: return
        val gson = Gson()
        editor.putString(userLabel, gson.toJson(usr))
        editor.apply()
    }

    fun getUser(): UserDataModel? {
        val usr = sharedPreferences?.getString(userLabel, null) ?: return null
        val gson = Gson()
        return gson.fromJson(usr, UserDataModel::class.java)
    }

    fun deleteUser() {
        val editor = sharedPreferences?.edit() ?: return
        editor.remove(userLabel)
        editor.apply()
    }
}