package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding :ActivityMainBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        binding!!.tvTitleSlashScreen.alpha = 0f
        binding!!.tvTitleSlashScreen.animate().setDuration(3000).alpha(1f).withEndAction{
            val i = Intent(this, DashboardActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}