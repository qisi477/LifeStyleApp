package com.example.lifestyleapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.*
import com.example.lifestyleapp.fragments.MenuFragment
import com.example.lifestyleapp.fragments.SettingFragment
import com.example.lifestyleapp.fragments.SummaryFragment
import com.example.lifestyleapp.fragments.WeatherFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MenuFragment.DataParser, SettingFragment.SettingData,
    View.OnClickListener {
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
        val calculateData = CalculateData(
            userModel.calculateBMI(),
            userModel.calculateBMR(),
            userModel.calculateDailyCaloriesNeededForGoal()
        )

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
        when (p0?.id) {
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
                val calculateData = CalculateData(
                    userModel.calculateBMI(),
                    userModel.calculateBMR(),
                    userModel.calculateDailyCaloriesNeededForGoal()
                )
                val summaryFragment = SummaryFragment.newInstance(usr, calculateData)
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_detail, summaryFragment, "summary_frag")
                fragmentTransaction.commit()
            }
            Signals.LOGOUT -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
            Signals.WEATHER -> {
                //todo crashes on rotate
                val localData = LocalData(this)
                val usr = localData.getUser() ?: fakeUser2
                val weatherFragment = WeatherFragment.newInstance(usr)
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_detail, weatherFragment, "weather_frag")
                fragmentTransaction.commit()
            }
            Signals.HIKE -> {
                val localData = LocalData(this)
                val usr = localData.getUser() ?: fakeUser2
                val city = usr.city ?: "me"
                val gmmIntentUri =
                    Uri.parse("geo:0,0?q=trails near $city")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
            Signals.SETTING -> {
                val localData = LocalData(this)
                val usr = localData.getUser() ?: fakeUser2
                val settingFragment = SettingFragment.newInstance(usr)
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame_detail, settingFragment, "summary_frag")
                fragmentTransaction.commit()
            }
        }
    }

    override fun settingDataHandler(
        height: String,
        weight: String,
        goal: String,
        activityLevel: String
    ) {
        Log.d(
            TAG_XX,
            "Changed: height($height) weight($weight) goal($goal) active($activityLevel)"
        )
        val localData = LocalData(this)
        val usr = localData.getUser() ?: fakeUser2
        if (height != "") usr.heightInches = height.toInt()
        if (weight != "") usr.weightLbs = weight.toInt()
        if (goal != "") usr.weightChangeGoalPerWeek = goal.toFloat()
        if (activityLevel != "") usr.activityLevel = activityLevel
        val userModel = UserModel(usr)
        val calculateData = CalculateData(
            userModel.calculateBMI(),
            userModel.calculateBMR(),
            userModel.calculateDailyCaloriesNeededForGoal()
        )
        val summaryFragment = SummaryFragment.newInstance(usr, calculateData)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_detail, summaryFragment, "summary_frag")
        fragmentTransaction.commit()
    }
}
