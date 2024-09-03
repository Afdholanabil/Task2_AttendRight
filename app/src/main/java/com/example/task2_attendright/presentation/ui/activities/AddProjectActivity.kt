package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.task2_attendright.databinding.ActivityAddProjectBinding

class AddProjectActivity : AppCompatActivity() {
    private var _binding: ActivityAddProjectBinding? = null
    private val binding get() = _binding!!
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddProjectBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        binding.btnArrowBackProject.setOnClickListener { onBackPressed() }

        binding.txtMinusPointProject.setOnClickListener {
            if (count >= 1000) {
                count -= 1000
                binding.txtValuePointProject.text = count.toString()
            }
        }

        binding.txtPlusPointProject.setOnClickListener {
            count += 1000
            binding.txtValuePointProject.text = count.toString()
        }

        binding.icUserAddProject.setOnClickListener {
            startActivity(Intent(this, AddMembersProjectActivity::class.java))
        }
    }
}