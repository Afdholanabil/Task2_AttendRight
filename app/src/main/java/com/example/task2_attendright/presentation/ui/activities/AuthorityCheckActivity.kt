package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.datastore.AuthorityDataStore
import com.example.task2_attendright.databinding.ActivityAuthorityCheckBinding
import com.example.task2_attendright.presentation.ui.animation.AnimationUtil
import kotlinx.coroutines.launch

class AuthorityCheckActivity : AppCompatActivity() {
    private var _binding: ActivityAuthorityCheckBinding? = null
    private val binding get() = _binding!!

    private val authorityDataStore by lazy { AuthorityDataStore(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthorityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            switchOpenLoc.setOnCheckedChangeListener { _, _ -> checkSwitch() }
            switchOpenCam.setOnCheckedChangeListener { _, _ -> checkSwitch() }
            switchEnableLocData.setOnCheckedChangeListener { _, _ -> checkSwitch() }
        }
    }

    private fun checkSwitch() {
        val authLocPer = binding.switchOpenLoc.isChecked
        val authCamPer = binding.switchOpenCam.isChecked
        val authLocData = binding.switchEnableLocData.isChecked

        if (authLocData && authLocPer && authCamPer) {
            // Simpan izin di DataStore
            lifecycleScope.launch {
                authorityDataStore.setPermissionGranted(true)
                Toast.makeText(this@AuthorityCheckActivity, "Semua izin diberikan!", Toast.LENGTH_SHORT).show()

                // Kembali ke fragment atau aktivitas sebelumnya
                val intent = Intent(this@AuthorityCheckActivity, location_activity_osm::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            Toast.makeText(this, "Harap berikan semua izin!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
