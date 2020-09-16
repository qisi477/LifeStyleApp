package com.example.lifestyleapp.activities

import android.content.Intent
import com.example.lifestyleapp.common.UserModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.*
import com.example.lifestyleapp.fragments.MenuFragment
import com.example.lifestyleapp.fragments.RegisterFragment
import com.example.lifestyleapp.fragments.SummaryFragment
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : AppCompatActivity(), MenuFragment.DataParser {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Log.d(TAG_XX, "Enter the summary activity")
        setContentView(R.layout.activity_summary)
        val localData = LocalData(this)
        val usr = localData.getUser() ?: fakeUser2
        val userModel = UserModel(usr)
        val calculateData = CalculateData(userModel.calculateBMI(), userModel.calculateBMR(), userModel.calculateDailyCaloriesNeededForGoal())
        val summaryFragment = SummaryFragment.newInstance(usr, calculateData)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (!isTablet()){
            menu_bt.setOnClickListener {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
            fragmentTransaction.replace(R.id.frame_layout, summaryFragment, "summary_frag")
        } else {
            val menuFragment = MenuFragment.newInstance(usr)
            fragmentTransaction.replace(R.id.frame_master, menuFragment, "menu_frag")
            fragmentTransaction.replace(R.id.frame_detail, summaryFragment, "summary_frag")
        }
        fragmentTransaction.commit()
    }

    private fun isTablet(): Boolean{
        return resources.getBoolean(R.bool.isTablet)
    }

    override fun dataHandler(command: Signals) {
        when (command) {
            Signals.SUMMARY -> {
                Log.d(TAG_XX, "Summary Activity takes over and change fragment")
                val localData = LocalData(this)
                val usr = localData.getUser() ?: fakeUser2
                val userModel = UserModel(usr)
                val calculateData = CalculateData(userModel.calculateBMI(), userModel.calculateBMR(), userModel.calculateDailyCaloriesNeededForGoal())
                val summaryFragment = SummaryFragment.newInstance(usr, calculateData)
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_detail, summaryFragment, "summary_frag")
                fragmentTransaction.commit()
            }
            Signals.LOGOUT -> {
                Log.d(TAG_XX, "Summary Activity takes over and start register")
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            Signals.HIKE -> TODO()
            Signals.WEATHER -> TODO()
            Signals.Setting -> TODO()
        }
    }
}