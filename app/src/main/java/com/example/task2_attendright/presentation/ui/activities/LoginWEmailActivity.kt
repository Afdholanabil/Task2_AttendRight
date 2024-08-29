package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityLoginWemailBinding
import com.example.task2_attendright.presentation.ui.animation.AnimationUtil

class LoginWEmailActivity : AppCompatActivity() {
    private var _binding : ActivityLoginWemailBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityLoginWemailBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        binding!!.btnSignin.setOnClickListener {
            intent = Intent(this, DashboardActivity::class.java)
            AnimationUtil.startActivityWithSlideAnimation(this, intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}