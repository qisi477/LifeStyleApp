package com.example.lifestyleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifestyleapp.common.CalculateData
import com.example.lifestyleapp.common.LocalData
import com.example.lifestyleapp.common.UserModel
import com.example.lifestyleapp.common.fakeUser2
import com.example.lifestyleapp.fragments.MenuFragment
import com.example.lifestyleapp.fragments.SummaryFragment
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val localData = LocalData(this)
        val usr = localData.getUser() ?: fakeUser2
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val menuFragment = MenuFragment.newInstance(usr, "")
        fragmentTransaction.replace(R.id.frame_master, menuFragment, "menu_frag")
        fragmentTransaction.commit()
        back_bt.setOnClickListener {
            finish()
        }
    }
}