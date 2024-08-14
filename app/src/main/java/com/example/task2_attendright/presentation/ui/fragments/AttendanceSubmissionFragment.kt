package com.example.task2_attendright.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.SubmissionList
import com.example.task2_attendright.databinding.FragmentAttendanceSubmissionBinding
import com.example.task2_attendright.presentation.ui.adapter.SubmissionAdapter
import com.google.android.material.tabs.TabLayout


class AttendanceSubmissionFragment : Fragment() {

    private var _bindings : FragmentAttendanceSubmissionBinding? = null
    private val binding get() = _bindings

    private lateinit var submissionAdapter: SubmissionAdapter
    private lateinit var allItems: List<SubmissionList>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _bindings = FragmentAttendanceSubmissionBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding!!.rvSubmission
        val tabLayout = binding!!.tabsFilterSubmission

        // Data dummy sebagai contoh
        allItems = listOf(
            SubmissionList("Cuti Hari Raya Keluarga", "Leave Request", "Pending","24 Nov 2023 - 26 Nov 2024" ),
            SubmissionList("Cuti Hari Raya Keluarga", "Leave Request", "Approved", "24 Nov 2023 - 26 Nov 2024"),
            SubmissionList("Cuti Hari Raya Keluarga", "Leave Request", "Declined", "24 Nov 2023 - 26 Nov 2024"),
            SubmissionList("Pengajuan Ganti Ke shift..", "Shift Change", "Pending", "32 Nov 2024")
        )

        // Setup RecyclerView
        submissionAdapter = SubmissionAdapter(allItems)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = submissionAdapter

        // Setup TabLayout
        setupTabLayout(tabLayout)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AttendanceSubmissionFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun setupTabLayout(tabLayout: TabLayout) {
        val tabs = listOf("All", "Work", "Leave", "Shift", "Attendance")
        tabs.forEach { tabLayout.addTab(tabLayout.newTab().setText(it)) }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                filterList(tab?.text.toString())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun filterList(filter: String) {
        val filteredList = when (filter) {
            "All" -> allItems
            "Work" -> allItems.filter { it.mode == "Work" }
            "Leave" -> allItems.filter { it.mode == "Leave Request" }
            "Shift" -> allItems.filter { it.mode == "Shift Change" }
            else -> allItems
        }
        submissionAdapter.updateData(filteredList)
    }
}