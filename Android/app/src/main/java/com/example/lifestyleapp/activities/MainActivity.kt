package com.example.lifestyleapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.*
import com.example.lifestyleapp.fragments.MenuFragment
import com.example.lifestyleapp.fragments.SettingFragment
import com.example.lifestyleapp.fragments.SummaryFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MenuFragment.DataParser, View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // load user info
        val localData = LocalData(this)
        localData.saveUser(fakeUser)
        if (localData.getUser() == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        val usr = localData.getUser() ?: fakeUser2
        val userModel = UserModel(usr)
        val calculateData = CalculateData(userModel.calculateBMI(), userModel.calculateBMR(), userModel.calculateDailyCaloriesNeededForGoal())

        // try to start summary and menu
        val summaryFragment = SummaryFragment.newInstance(usr, calculateData)
        val menuFragment = MenuFragment.newInstance(usr)
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // split behavior according to device type
        if (!isTablet()) {
            menu_bt?.setOnClickListener(this)
        } else {
            fragmentTransaction.replace(R.id.frame_master, menuFragment, "menu_frag")
        }
        fragmentTransaction.replace(R.id.frame_detail, summaryFragment, "summary_frag")
        fragmentTransaction.commit()
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.menu_bt -> {
                val localData = LocalData(this)
                val usr = localData.getUser() ?: fakeUser2
                val menuFragment = MenuFragment.newInstance(usr)
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_detail, menuFragment, "menu_frag")
                fragmentTransaction.commit()
            }
        }
    }

    private fun isTablet(): Boolean {
        return resources.getBoolean(R.bool.isTablet)
    }

    override fun dataHandler(command: Signals) {
        when (command) {
            Signals.SUMMARY -> {
                val localData = LocalData(this)
                val usr = localData.getUser() ?: fakeUser2
                val userModel = UserModel(usr)
                val calculateData = CalculateData(userModel.calculateBMI(),
                    userModel.calculateBMR(),
                    userModel.calculateDailyCaloriesNeededForGoal())
                val summaryFragment = SummaryFragment.newInstance(usr, calculateData)
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_detail, summaryFragment, "summary_frag")
                fragmentTransaction.commit()
            }
            Signals.LOGOUT -> {
                Log.d(TAG_XX, "Summary Activity takes over and start register")
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
            Signals.HIKE -> TODO()
            Signals.WEATHER -> {
                Log.d(TAG_XX, "Summary Activity takes over and start weather")
                val intent = Intent(this, WeatherActivity::class.java)
                startActivity(intent)
            }
            Signals.SETTING -> {
                val settingFragment = SettingFragment.newInstance("", "")
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_detail, settingFragment, "summary_frag")
                fragmentTransaction.commit()
            }
        }
    }
}