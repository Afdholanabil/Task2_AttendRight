package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task2_attendright.databinding.ActivitySuccessInformationClockInBinding
import com.example.task2_attendright.presentation.ui.animation.AnimationUtil

class success_information_clock_in : AppCompatActivity() {
    private var _binding: ActivitySuccessInformationClockInBinding?= null
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
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
}