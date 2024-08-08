package com.example.task2_attendright.presentation.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task2_attendright.presentation.ui.activities.PoinActivity
import com.example.task2_attendright.presentation.ui.fragments.MonthFragment

class HistoryMonthAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 12
    }

    override fun createFragment(position: Int): Fragment {
        return MonthFragment.newInstance(position)
    }
}