package com.example.task2_attendright.presentation.ui

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityFragmentProjectBinding
import com.example.task2_attendright.presentation.fragments.ProjectFragment

class fragment_project : AppCompatActivity() {
    private var _binding: ActivityFragmentProjectBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFragmentProjectBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        if (findViewById<FrameLayout>(R.id.fragment_project) != null) {
            if (savedInstanceState != null) {
                return
            }
            val exampleFragment = ProjectFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_project, exampleFragment)
                .commit()
        }
    }
}