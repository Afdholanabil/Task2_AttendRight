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
    private var binding : ActivityLoginWemailBinding? = null
    private val _binding get() = binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginWemailBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding!!.btnSignin.setOnClickListener {
            intent = Intent(this, DashboardActivity::class.java)
            AnimationUtil.startActivityWithSlideAnimation(this, intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}