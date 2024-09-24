package com.example.task2_attendright.presentation.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.postDelayed
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.viewpager2.widget.ViewPager2
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.FragmentAttendanceBinding
import com.example.task2_attendright.presentation.ui.adapter.AttendanceTabAdapter
import com.example.task2_attendright.presentation.ui.adapter.MonthAdapter
import com.example.task2_attendright.presentation.viewmodel.AttendanceViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Calendar

class AttendanceFragment : Fragment(), MonthAdapter.CenterMonthListener{
    private var _binding : FragmentAttendanceBinding? = null
    private val binding get() = _binding
    private lateinit var monthAdapter: MonthAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var selectedMonth: Int = Calendar.getInstance().get(Calendar.MONTH)
    private var selectedYear: Int = Calendar.getInstance().get(Calendar.YEAR)

    private lateinit var attendanceViewModel: AttendanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        attendanceViewModel = ViewModelProvider(requireActivity()).get(AttendanceViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttendanceBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.buttonYear.setOnClickListener {
            showYearPickerDialog(binding!!.buttonYear)
        }

        val tabLayoutAdapter = AttendanceTabAdapter(requireActivity())
        val viewPagerTab : ViewPager2 =binding!!.vpTabAttendance
        viewPagerTab.adapter = tabLayoutAdapter
        val tabs : TabLayout = binding!!.tabLayoutAttendance

        val titleTab = listOf("Attendance","Submission")
        TabLayoutMediator(tabs,viewPagerTab){tab,ps ->
            tab.text = titleTab[ps]
        }.attach()

        val longMonth = resources.getStringArray(R.array.months_array).toList()
        val shortMonth = resources.getStringArray(R.array.months_array_short).toList()
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH)

        monthAdapter = MonthAdapter(longMonth, shortMonth, { selectedMonthIt ->

            selectedMonth = longMonth.indexOf(selectedMonthIt)
            sentSelectedMonthYearToFragment(selectedMonth, selectedYear)
            Log.d(TAG, "bulan yang dipilih $selectedMonth")
        }, this)

        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding?.rvDate1?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = monthAdapter
        }

        binding?.rvDate1?.post {
            monthAdapter.submitList(shortMonth)
            monthAdapter.selectedPosition = currentMonth
            monthAdapter.notifyItemChanged(currentMonth)
            centerSelectedMonth(currentMonth)
        }

        binding!!.buttonYear.setOnClickListener {
            showYearPickerDialog(binding!!.buttonYear)
        }
    }

    override fun onCenterMonth(position: Int) {
        centerSelectedMonth(position)
    }

    private fun centerSelectedMonth(position: Int) {
        Log.d(TAG, "centerSelectedMonth called for position: $position")

        binding?.rvDate1?.let { recyclerView ->
            val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
            if (layoutManager == null || layoutManager.findViewByPosition(position) == null || !recyclerView.isLaidOut || !recyclerView.isAttachedToWindow) {
                Log.e(TAG, "LayoutManager or View at position $position is not ready, scrolling to position directly")
                layoutManager?.scrollToPositionWithOffset(position, calculateCenterOffset(position))

                recyclerView.postDelayed({
                    Log.d(TAG, "Attempting to scroll after delay for position: $position")
                    if (recyclerView.isLaidOut && recyclerView.isAttachedToWindow) {
                        val view = layoutManager?.findViewByPosition(position)
                        if (view == null) {
                            Log.e(TAG, "View still not ready, retrying scroll to position $position")
                            layoutManager?.scrollToPositionWithOffset(position, calculateCenterOffset(position))
                        } else {
                            Log.d(TAG, "View is now ready, successfully centered position: $position")
                        }
                    } else {
                        Log.e(TAG, "RecyclerView is still not ready after delay, fallback to normal scroll")
                        layoutManager?.scrollToPosition(position)
                    }
                }, 300)
            } else {
                Log.d(TAG, "LayoutManager and View at position $position are ready, scrolling to position with offset")
                layoutManager.scrollToPositionWithOffset(position, calculateCenterOffset(position))
            }
        } ?: run {
            Log.e(TAG, "RecyclerView is null, cannot scroll")
        }
    }

    private fun calculateCenterOffset(position: Int): Int {
        val view = layoutManager.findViewByPosition(position)
        val itemWidth = view?.width ?: 200
        val screenWidth = resources.displayMetrics.widthPixels
        return (screenWidth/2) - (itemWidth/2)
    }

    private fun showYearPickerDialog(button: MaterialButton) {
        val years = (2000..2100).toList().map { it.toString() }.toTypedArray()
        var selectedYearString = button.text.toString()

        AlertDialog.Builder(requireActivity())
            .setTitle("Pilih Tahun")
            .setSingleChoiceItems(years, years.indexOf(selectedYearString)) { dialog, which ->
                selectedYearString = years[which]
            }
            .setPositiveButton("OK") { dialog, _ ->
                button.text = selectedYearString
                selectedYear = selectedYearString.toInt()
                sentSelectedMonthYearToFragment(selectedMonth,selectedYear)
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun sentSelectedMonthYearToFragment(month: Int, year: Int) {
        val viewPager = binding?.vpTabAttendance
        val currentFragment = childFragmentManager.fragments.find {
            it is AttendanceAttendFragment && it.isVisible
        } as? AttendanceAttendFragment

        if (currentFragment != null) {
            currentFragment.updateDataForMonthYear(month, year)
        } else {
            Log.e(TAG, "Fragment yang sedang ditampilkan bukan AttendanceAttendFragment atau tidak ditemukan")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AttendanceFragment().apply {
                arguments = Bundle().apply {

                }
            }
        private const val TAG = "AttendanceFragment"
    }
}