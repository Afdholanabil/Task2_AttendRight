package com.example.task2_attendright.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.viewpager2.widget.ViewPager2
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.ActivityDashboardBinding
import com.example.task2_attendright.presentation.ui.adapter.DashboardAdapter
import com.example.task2_attendright.presentation.ui.animation.AnimationUtil
import com.example.task2_attendright.presentation.ui.fragments.AttendanceFragment
import com.example.task2_attendright.presentation.ui.fragments.HomeFragment
import com.example.task2_attendright.presentation.ui.fragments.MeetFragment
import com.example.task2_attendright.presentation.ui.fragments.ProfileFragment
import com.example.task2_attendright.presentation.ui.fragments.ProjectFragment

class DashboardActivity : AppCompatActivity() {

    private var _binding : ActivityDashboardBinding? = null
    private val binding get() = _binding
    private var currentFragment : Fragment? = null
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


        val bottoNav = binding!!.bottomNavDashboard
        val fragmentToOpen = intent.getIntExtra("FRAGMENT_TO_OPEN", 0)


        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")
        Log.d(TAG, "data date n time : $date,$time")

        if (savedInstanceState == null) {
            when(fragmentToOpen) {
                0 ->  {
                    replaceFragment(HomeFragment.newInstance(date,time))
                    bottoNav.selectedItemId = R.id.navigation_home
                }
                1 -> {
                    replaceFragment(AttendanceFragment())
                    bottoNav.selectedItemId = R.id.navigation_attendance
                }
                2 -> {
                    replaceFragment(ProjectFragment())
                    bottoNav.selectedItemId = R.id.navigation_project
                }
                3 -> {
                    replaceFragment(MeetFragment())
                    bottoNav.selectedItemId = R.id.navigation_meet
                }
                4 -> {
                    replaceFragment(ProfileFragment())
                    bottoNav.selectedItemId = R.id.navigation_profile
                }
                else -> {
                    replaceFragment(HomeFragment.newInstance(date, time))
                    bottoNav.selectedItemId = R.id.navigation_home
                }
            }

        }

        bottoNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment.newInstance(date, time))
                    true
                }
                R.id.navigation_attendance -> {
                    replaceFragment(AttendanceFragment())
                    true
                }
                R.id.navigation_project -> {
                    replaceFragment(ProjectFragment())
                    true
                }
                R.id.navigation_meet -> {
                    replaceFragment(MeetFragment())
                    true
                }
                R.id.navigation_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment::class != currentFragment?.javaClass){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_dashboard,fragment)
                .commit()
            currentFragment = fragment


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "DashboardActivtiy"
    }
}