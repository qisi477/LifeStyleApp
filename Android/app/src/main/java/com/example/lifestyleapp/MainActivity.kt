package com.example.lifestyleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lifestyleapp.activities.RegisterActivity
import com.example.lifestyleapp.activities.SummaryActivity
import com.example.lifestyleapp.common.LocalData
import com.example.lifestyleapp.common.fakeUser
import com.example.lifestyleapp.common.fakeUser2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val localData = LocalData(this)
        if (localData.getUser() == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, SummaryActivity::class.java)
            startActivity(intent)
        }
//        localData.saveUser(fakeUser)
    }
}