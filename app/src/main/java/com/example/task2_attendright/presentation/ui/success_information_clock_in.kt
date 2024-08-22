package com.example.task2_attendright.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityFinalClockInBinding
import com.example.task2_attendright.databinding.ActivitySuccessInformationClockInBinding

class success_information_clock_in : AppCompatActivity() {
    private var _binding: ActivitySuccessInformationClockInBinding ?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySuccessInformationClockInBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        val dataDay = intent.getStringExtra("date")
        val dataTime = intent.getStringExtra("time")

        binding.successAnimation.playAnimation()
        binding.txtSuccessDay.text = dataDay
        binding.txtSuccessTime.text = dataTime
        binding.btnSuccessInformation.setOnClickListener {
            val intent = Intent(this, fragmenttTestt::class.java)
            startActivity(intent)
        }
    }
}