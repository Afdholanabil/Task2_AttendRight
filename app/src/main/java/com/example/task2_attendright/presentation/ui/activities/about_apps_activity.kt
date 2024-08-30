package com.example.task2_attendright.presentation.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityAboutAppsBinding

class about_apps_activity : AppCompatActivity() {
    private var _binding: ActivityAboutAppsBinding ?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAboutAppsBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        binding.imgArrowBack2.setOnClickListener { onBackPressed() }
    }
}