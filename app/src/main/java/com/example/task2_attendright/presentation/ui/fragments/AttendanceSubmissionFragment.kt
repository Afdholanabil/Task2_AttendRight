package com.example.task2_attendright.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.SubmissionList
import com.example.task2_attendright.databinding.FragmentAttendanceSubmissionBinding
import com.example.task2_attendright.presentation.ui.adapter.SubmissionAdapter
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class AttendanceSubmissionFragment : Fragment() {

    private var _bindings : FragmentAttendanceSubmissionBinding? = null
    private val binding get() = _bindings

    private lateinit var submissionAdapter: SubmissionAdapter
    private lateinit var allItems: List<SubmissionList>

    private var isFabOpen : Boolean = false
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

        val fabAddSubmission = binding!!.fabAddSubmission
        val fabAttendance = binding!!.fabAttendance
        val fabLeaveReq = binding!!.fabLeaveRequest
        val fabShiftChange = binding!!.fabShiftChange

        allItems = listOf(
            SubmissionList("Cuti Hari Raya Keluarga", "Leave Request", "Pending","24 Nov 2023 - 26 Nov 2024" ),
            SubmissionList("Cuti Hari Raya Keluarga", "Leave Request", "Approved", "24 Nov 2023 - 26 Nov 2024"),
            SubmissionList("Cuti Hari Raya Keluarga", "Leave Request", "Declined", "24 Nov 2023 - 26 Nov 2024"),
            SubmissionList("Pengajuan Ganti Ke shift..", "Shift Change", "Pending", "32 Nov 2024")
        )

        submissionAdapter = SubmissionAdapter(allItems)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = submissionAdapter

        setupTabLayout(tabLayout)

        fabAddSubmission.setOnClickListener {
            if (isFabOpen) {
                closeFab(fabAttendance,fabShiftChange,fabLeaveReq)
            } else {
                openFab(fabAttendance,fabShiftChange,fabLeaveReq)
            }
        }
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
        val tabTitles = listOf("All", "Work", "Leave", "Shift", "Attendance")

        // Menambahkan tab ke TabLayout dengan custom view
        tabTitles.forEachIndexed { index, title ->
            val tab = tabLayout.newTab()
            tab.customView = createTabView(title)
            tabLayout.addTab(tab)
        }

        // Menangani event saat tab dipilih
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.let {
                    val tabTitle = it.findViewById<TextView>(R.id.tv_tab_title_submission)
                    val cardView = it.findViewById<MaterialCardView>(R.id.cv_tab_title_submission)
                    tabTitle.setTextColor(resources.getColor(R.color.gray800)) // Warna saat tab dipilih
                    cardView.setCardBackgroundColor(resources.getColor(R.color.gray100))
                }
                filterList(tabTitle(tabLayout.selectedTabPosition)) // Filter list berdasarkan tab yang dipilih
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.let {
                    val tabTitle = it.findViewById<TextView>(R.id.tv_tab_title_submission)
                    val cardView = it.findViewById<MaterialCardView>(R.id.cv_tab_title_submission)
                    tabTitle.setTextColor(resources.getColor(R.color.gray400)) // Warna saat tab tidak dipilih
                    cardView.setCardBackgroundColor(resources.getColor(R.color.gray100))
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun createTabView(title: String): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tab_title_submission, null)
        val tabTitle = view.findViewById<TextView>(R.id.tv_tab_title_submission)
        tabTitle.text = title
        return view
    }

    private fun filterList(filter: String) {
        val filteredList = when (filter) {
            "All" -> allItems
            "Work" -> allItems.filter { it.mode == "Work" }
            "Leave" -> allItems.filter { it.mode == "Leave Request" }
            "Shift" -> allItems.filter { it.mode == "Shift Change" }
            "Attendance" -> allItems.filter { it.mode == "Attendance" }
            else -> allItems
        }
        submissionAdapter.updateData(filteredList)
        if (filteredList.isEmpty()) {
            binding!!.rvSubmission.visibility = View.GONE
            binding!!.tvSubmissionIfNull.visibility = View.VISIBLE
            binding!!.ivSubmissionIfNull.visibility = View.VISIBLE
        } else {
            binding!!.rvSubmission.visibility = View.VISIBLE
            binding!!.tvSubmissionIfNull.visibility = View.GONE
            binding!!.ivSubmissionIfNull.visibility = View.GONE
        }
    }

    private fun tabTitle(position:Int) :String {
        val tabTitle = listOf("All","Work","Leave","Shift","Attendance")
        return tabTitle[position]
    }

    private fun openFab(vararg fabs : FloatingActionButton) {
        fabs.forEach { it.visibility = View.VISIBLE }
        isFabOpen = true

    }

    private fun closeFab(vararg fabs : FloatingActionButton) {
        fabs.forEach { it.visibility = View.GONE }
        isFabOpen = false
    }
}