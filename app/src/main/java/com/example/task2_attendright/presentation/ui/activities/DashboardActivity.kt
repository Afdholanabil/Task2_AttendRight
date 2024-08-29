package com.example.task2_attendright.presentation.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityDashboardBinding
import com.example.task2_attendright.presentation.ui.adapter.DashboardAdapter

class DashboardActivity : AppCompatActivity() {

    private var _binding : ActivityDashboardBinding? = null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            binding!!.bottomNavDashboard.setPadding(0, 0, 0, systemBars.bottom)
            insets
        }

        val viewPager : ViewPager2 = binding!!.fragmentContainerDashboard
        val bottoNav = binding!!.bottomNavDashboard

        viewPager.adapter =DashboardAdapter(this)
        bottoNav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.navigation_attendance -> {
                    viewPager.currentItem = 1
                    true
                }
                R.id.navigation_project -> {
                    viewPager.currentItem = 2
                    true
                }
                R.id.navigation_meet -> {
                    viewPager.currentItem = 3
                    true
                }
                R.id.navigation_profile -> {
                    viewPager.currentItem = 4
                    true
                }
                else -> false
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottoNav.menu.getItem(position).isChecked =true
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}