package com.example.task2_attendright.presentation.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task2_attendright.presentation.ui.fragments.AttendanceFragment
import com.example.task2_attendright.presentation.ui.fragments.HomeFragment
import com.example.task2_attendright.presentation.ui.fragments.MeetFragment
import com.example.task2_attendright.presentation.ui.fragments.ProfileFragment
import com.example.task2_attendright.presentation.ui.fragments.ProjectFragment

class DashboardAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> AttendanceFragment()
            2 -> ProjectFragment()
            3 -> MeetFragment()
            4 -> ProfileFragment()
            else -> Fragment()
        }
    }
}