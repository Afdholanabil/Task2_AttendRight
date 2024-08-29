package com.example.task2_attendright.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task2_attendright.databinding.ActivityJobInformationBinding

class job_information_activity : AppCompatActivity() {
    private lateinit var binding: ActivityJobInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}