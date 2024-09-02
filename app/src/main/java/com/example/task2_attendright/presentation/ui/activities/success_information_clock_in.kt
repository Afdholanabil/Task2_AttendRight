package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ContentInfoCompat.Flags
import com.example.task2_attendright.databinding.ActivitySuccessInformationClockInBinding

class success_information_clock_in : AppCompatActivity() {
    private var _binding: ActivitySuccessInformationClockInBinding?= null
    private val binding get() = _binding!!
    private lateinit var dateA : String
    private lateinit var timeA : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySuccessInformationClockInBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        val dataDay = intent.getStringExtra("date")
        val dataTime = intent.getStringExtra("time")
        dateA = dataDay.toString()
        timeA = dataTime.toString()

        binding.successAnimation.playAnimation()
        binding.txtSuccessDay.text = dataDay
        binding.txtSuccessTime.text = dataTime
        binding.btnSuccessInformation.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra("date",dataDay)
            intent.putExtra("time",dataTime)

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}