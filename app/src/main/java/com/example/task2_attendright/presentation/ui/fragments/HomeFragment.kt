package com.example.task2_attendright.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.MeetingToday
import com.example.task2_attendright.data.local.TodayTask
import com.example.task2_attendright.databinding.FragmentHomeBinding
import com.example.task2_attendright.presentation.ui.adapter.MeetingTodayAdapter
import com.example.task2_attendright.presentation.ui.adapter.TodayTasksAdapter


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        val recyclerViewTodayTask = binding!!.rvTodayTasks
        val recyclerViewTodayMeeting = binding!!.rvMeetingToday
        recyclerViewTodayTask.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        recyclerViewTodayMeeting.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)

        binding!!.rvTodayTasks.isNestedScrollingEnabled = false
        binding!!.rvMeetingToday.isNestedScrollingEnabled = false
        val dummyTasks = listOf(
            TodayTask("08:00 WIB", "Daily Standup Meeting"),
            TodayTask("10:00 WIB", "Design Review"),
            TodayTask("13:00 WIB", "Development Sprint")
        )

        val dummyMeetings = listOf(
            MeetingToday("Meeting Project Wisata", "Ruang Rapat 1", "Offline"),
            MeetingToday("Sync-up Meeting", "Zoom", "Online"),
            MeetingToday("Brainstorming Session", "Café Near Office", "Offline")
        )

        recyclerViewTodayTask.adapter = TodayTasksAdapter(dummyTasks)
        recyclerViewTodayMeeting.adapter = MeetingTodayAdapter(dummyMeetings)
        return binding!!.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}