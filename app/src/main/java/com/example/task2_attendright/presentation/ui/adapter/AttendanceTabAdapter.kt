package com.example.task2_attendright.presentation.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task2_attendright.presentation.ui.fragments.AttendanceAttendFragment
import com.example.task2_attendright.presentation.ui.fragments.AttendanceSubmissionFragment

class AttendanceTabAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0-> fragment = AttendanceAttendFragment()
            1-> fragment = AttendanceSubmissionFragment()
        }
        return fragment as Fragment
    }
}