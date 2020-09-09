package com.example.lifestyleapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.FragmentTransaction
import com.example.lifestyleapp.R
import com.example.lifestyleapp.fragments.SummaryFragment

class SummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SummaryPage", "Enter the summary activity")
        setContentView(R.layout.activity_summary)
        val summaryFragment = SummaryFragment.newInstance("Test1", "Test2")
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, summaryFragment, "summary_frag")
        fragmentTransaction.commit()
    }
}