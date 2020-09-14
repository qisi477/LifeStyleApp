package com.example.lifestyleapp.activities

import com.example.lifestyleapp.common.UserModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.*
import com.example.lifestyleapp.fragments.SummaryFragment

class SummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val localData = LocalData(this)
        Log.d(TAG_XX, "Enter the summary activity")
        setContentView(R.layout.activity_summary)
        val usr = localData.getUser() ?: fakeUser2
        val userModel = UserModel(usr)
        val calculateData = CalculateData(userModel.calculateBMI(), userModel.calculateBMR(), userModel.calculateDailyCaloriesNeededForGoal())
        val summaryFragment = SummaryFragment.newInstance(usr, calculateData)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, summaryFragment, "summary_frag")
        fragmentTransaction.commit()
    }
}