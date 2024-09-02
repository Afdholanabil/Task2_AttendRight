package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityAuthorityCheckBinding
import com.example.task2_attendright.presentation.ui.animation.AnimationUtil

class AuthorityCheckActivity : AppCompatActivity() {

    private var _binding : ActivityAuthorityCheckBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityAuthorityCheckBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupSwitchListeners()
    }

    private fun setupSwitchListeners() {
        binding?.apply {
            switchOpenLoc.setOnCheckedChangeListener { _, _ -> checkSwitch() }
            switchOpenCam.setOnCheckedChangeListener { _, _ -> checkSwitch() }
            switchEnableLocData.setOnCheckedChangeListener { _, _ -> checkSwitch() }
        }
    }

    private fun checkSwitch() {
        val authLocPer = binding!!.switchOpenLoc.isChecked
        val authCamPer = binding!!.switchOpenCam.isChecked
        val authLocData = binding!!.switchEnableLocData.isChecked

        if (authLocData && authLocPer && authCamPer) {
            val intent = Intent(this, location_activity_osm::class.java)
            AnimationUtil.startActivityWithSlideAnimation(this, intent)
        } else {
            Toast.makeText(
                this,
                "Aktivitas tidak bisa dilanjutkan! Harap izinkan semua aplikasi.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "AuthorityCheckActivity"
    }
}