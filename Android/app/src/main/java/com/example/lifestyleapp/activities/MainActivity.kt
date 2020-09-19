package com.example.lifestyleapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lifestyleapp.R
import com.example.lifestyleapp.common.LocalData
import com.example.lifestyleapp.common.fakeUser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val localData = LocalData(this)
        localData.saveUser(fakeUser)
        if (localData.getUser() == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, SummaryActivity::class.java)
            startActivity(intent)
        }
    }
}