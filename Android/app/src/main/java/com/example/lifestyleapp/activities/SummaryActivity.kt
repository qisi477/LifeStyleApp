package com.example.lifestyleapp.activities

import UserModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.FragmentTransaction
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.CalculateData
import com.example.lifestyleapp.common.TAG_XX
import com.example.lifestyleapp.common.fakeUser
import com.example.lifestyleapp.fragments.SummaryFragment

class SummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_XX, "Enter the summary activity")
        setContentView(R.layout.activity_summary)
        val userModel = UserModel(fakeUser)
        val calculateData = CalculateData(userModel.calculateBMI(), userModel.calculateBMR())
        val summaryFragment = SummaryFragment.newInstance(fakeUser, calculateData)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, summaryFragment, "summary_frag")
        fragmentTransaction.commit()
    }
}