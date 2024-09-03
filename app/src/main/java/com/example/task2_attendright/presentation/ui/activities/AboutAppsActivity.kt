package com.example.task2_attendright.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task2_attendright.databinding.ActivityAboutAppsBinding

class AboutAppsActivity : AppCompatActivity() {
    private var _binding: ActivityAboutAppsBinding ?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAboutAppsBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        binding.imgArrowBack2.setOnClickListener { onBackPressed() }
    }
}