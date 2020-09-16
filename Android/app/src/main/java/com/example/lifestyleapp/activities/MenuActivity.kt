package com.example.lifestyleapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.*
import com.example.lifestyleapp.fragments.MenuFragment
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity(), MenuFragment.DataParser {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val localData = LocalData(this)
        val usr = localData.getUser() ?: fakeUser2
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val menuFragment = MenuFragment.newInstance(usr)
        fragmentTransaction.replace(R.id.frame_master, menuFragment, "menu_frag")
        fragmentTransaction.commit()
        back_bt.setOnClickListener {
            finish()
        }
    }

    override fun dataHandler(command: Signals) {
        when (command) {
            Signals.SUMMARY -> {
                Log.d(TAG_XX, "Menu Activity takes over and start summary activity")
                val intent = Intent(this, SummaryActivity::class.java)
                startActivity(intent)
            }
            Signals.LOGOUT -> {
                Log.d(TAG_XX, "Menu Activity takes over and start logout activity")
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            Signals.HIKE -> TODO()
            Signals.WEATHER -> TODO()
            Signals.Setting -> TODO()
        }
    }
}