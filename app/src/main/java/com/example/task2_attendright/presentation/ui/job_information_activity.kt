package com.example.task2_attendright.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityJobInformationBinding

class job_information_activity : AppCompatActivity() {
    private lateinit var binding: ActivityJobInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}