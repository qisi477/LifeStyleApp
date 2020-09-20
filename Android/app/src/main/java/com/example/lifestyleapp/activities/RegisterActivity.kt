package com.example.lifestyleapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lifestyleapp.R
import com.example.lifestyleapp.fragments.RegisterFragment

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("RegisterPage", "Enter the register activity")
        setContentView(R.layout.activity_register)
        val registerFragment = RegisterFragment.newInstance("Test1", "Test2")
        val registerFragmentTransaction = supportFragmentManager.beginTransaction()
        registerFragmentTransaction.replace(R.id.frame_detail, registerFragment, "register_frag")
        registerFragmentTransaction.commit()

        
    }
}