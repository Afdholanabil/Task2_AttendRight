package com.example.task2_attendright.presentation.ui.fragments

import android.content.Intent
import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.SubmissionList
import com.example.task2_attendright.databinding.FragmentAttendanceSubmissionBinding
import com.example.task2_attendright.presentation.ui.activities.AttendanceDetailActivity
import com.example.task2_attendright.presentation.ui.activities.LeaveReqDetailActivity
import com.example.task2_attendright.presentation.ui.activities.ShiftChangeDetailActivity
import com.example.task2_attendright.presentation.ui.activities.SubmissionFabActivity
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

        val tvAttendance = binding!!.tvFabAttendance
        val tvShiftChange = binding!!.tvFabShift
        val tvLeave = binding!!.tvFabLeave
        val rootLayout = binding!!.overlay


        allItems = listOf(
            SubmissionList("Cuti Hari Raya Keluarga", "Leave Request", "Pending","24 Nov 2023 - 26 Nov 2024" ),
            SubmissionList("Cuti Hari Raya Keluarga", "Leave Request", "Approved", "24 Nov 2023 - 26 Nov 2024"),
            SubmissionList("Cuti Hari Raya Keluarga", "Leave Request", "Declined", "24 Nov 2023 - 26 Nov 2024"),
            SubmissionList("Pengajuan Ganti Ke shift..", "Shift Change", "Pending", "32 Nov 2024")
        )

        submissionAdapter = SubmissionAdapter().apply {
            setOnItemClickListener { item ->
                navigateToDetail(item)
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = submissionAdapter
        submissionAdapter.submitList(allItems)

        setupTabLayout(tabLayout)

        fabAddSubmission.setOnClickListener {
            if (isFabOpen) {
                closeFab(rootLayout, fabAddSubmission, fabAttendance, fabShiftChange, fabLeaveReq, tvAttendance, tvLeave, tvShiftChange)
            } else {
                openFab(rootLayout, fabAddSubmission, tvAttendance, tvLeave, tvShiftChange, fabAttendance, fabShiftChange, fabLeaveReq, tvAttendance, tvLeave, tvShiftChange)
            }
        }

        goToFabFragment(fabAttendance, fabShiftChange, fabLeaveReq)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AttendanceSubmissionFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun navigateToDetail(item: SubmissionList) {
        val intent = when (item.mode) {
            "Leave Request" -> Intent(requireContext(), LeaveReqDetailActivity::class.java)
            "Shift Change" -> Intent(requireContext(), ShiftChangeDetailActivity::class.java)
            "Attendance" -> Intent(requireContext(), AttendanceDetailActivity::class.java)
            else -> null
        }
        intent?.let {
            startActivity(it)
        }
    }

    private fun setupTabLayout(tabLayout: TabLayout) {
        val tabTitles = listOf("All", "Work", "Leave", "Shift", "Attendance")

        tabTitles.forEachIndexed { index, title ->
            val tab = tabLayout.newTab()
            tab.customView = createTabView(title)
            tabLayout.addTab(tab)
        }


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.let {
                    val tabTitle = it.findViewById<TextView>(R.id.tv_tab_title_submission)
                    val cardView = it.findViewById<MaterialCardView>(R.id.cv_tab_title_submission)
                    tabTitle.setTextColor(resources.getColorStateList(R.color.text_month_selector, null))
                    cardView.setCardBackgroundColor(resources.getColorStateList(R.color.card_month_selector, null))
                }
                filterList(tabTitle(tabLayout.selectedTabPosition))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.let {
                    val tabTitle = it.findViewById<TextView>(R.id.tv_tab_title_submission)
                    val cardView = it.findViewById<MaterialCardView>(R.id.cv_tab_title_submission)
                    tabTitle.setTextColor(resources.getColor(R.color.gray400))
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
        submissionAdapter.submitList(filteredList)
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

    private fun openFab(rootLayout:FrameLayout,fabAddSubmission: FloatingActionButton,tvFab : TextView,tvFab2 : TextView,tvFab3 : TextView,vararg fabs : View) {
        rootLayout.visibility = View.VISIBLE


        fabAddSubmission.bringToFront()
        fabAddSubmission.translationZ = 10f
        tvFab.bringToFront()
        tvFab2.bringToFront()
        tvFab3.bringToFront()
        fabAddSubmission.animate().setDuration(300).rotation(45f).start()
        fabs.forEach {
            it.visibility = View.VISIBLE
            it.animate().alpha(1.0f).translationY(0f).setDuration(300).start()
        }

        isFabOpen = true

    }

    private fun closeFab(rootLayout:FrameLayout,fabAddSubmission: FloatingActionButton,vararg fabs : View) {
       rootLayout.visibility = View.GONE

        fabAddSubmission.animate().setDuration(300).rotation(0f).start()
        fabs.forEach {
            it.animate().alpha(0.0f).translationY(it.height.toFloat()).setDuration(300).withEndAction {
                it.visibility = View.GONE
            }.start()
        }
        isFabOpen = false
    }

    private fun goToFabFragment(fabAttendance: FloatingActionButton,fabShiftChange: FloatingActionButton,fabLeaveReq: FloatingActionButton){
        fabAttendance.setOnClickListener {
            val intent = Intent(requireContext(), SubmissionFabActivity::class.java)
            intent.putExtra("FRAGMENT_TYPE", "ATTENDANCE")
            startActivity(intent)
        }

        fabShiftChange.setOnClickListener {
            val intent = Intent(requireContext(), SubmissionFabActivity::class.java)
            intent.putExtra("FRAGMENT_TYPE", "SHIFT_CHANGE")
            startActivity(intent)
        }

        fabLeaveReq.setOnClickListener {
            val intent = Intent(requireContext(), SubmissionFabActivity::class.java)
            intent.putExtra("FRAGMENT_TYPE", "LEAVE")
            startActivity(intent)
        }

    }
}