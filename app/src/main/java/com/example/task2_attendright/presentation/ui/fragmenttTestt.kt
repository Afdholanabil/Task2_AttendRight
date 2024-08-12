package com.example.task2_attendright.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.presentation.fragments.MeetFragment

class fragmenttTestt : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragmentt_testt)
        if (findViewById<FrameLayout>(R.id.fragmenttest) != null) {
            if (savedInstanceState != null) {
                return
            }

            val exampleFragment = MeetFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmenttest, exampleFragment)
                .commit()
        }
    }
}