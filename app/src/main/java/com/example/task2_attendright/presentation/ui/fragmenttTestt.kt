package com.example.task2_attendright.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityFragmenttTesttBinding
import com.example.task2_attendright.presentation.fragments.MeetFragment

class fragmenttTestt : AppCompatActivity() {
    private var _binding: ActivityFragmenttTesttBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFragmenttTesttBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
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