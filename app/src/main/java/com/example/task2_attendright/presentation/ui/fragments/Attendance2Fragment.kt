package com.example.task2_attendright.presentation.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.task2_attendright.R
import com.example.task2_attendright.databinding.FragmentAttendance2Binding
import com.example.task2_attendright.presentation.ui.adapter.AttendanceTabAdapter
import com.example.task2_attendright.presentation.viewmodel.AttendanceViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Calendar

class Attendance2Fragment : Fragment() {
    private var _binding : FragmentAttendance2Binding? = null
    private val binding get() = _binding!!
    private lateinit var attendanceViewModel: AttendanceViewModel

    private lateinit var monthsArray: Array<String>
    private lateinit var monthsShortArray: Array<String>
    private var currentMonthIndex = 0

    private var selectedYear: Int = Calendar.getInstance().get(Calendar.YEAR)
    private var currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        attendanceViewModel = ViewModelProvider(requireActivity()).get(AttendanceViewModel::class.java)

        monthsArray = resources.getStringArray(R.array.months_array)
        monthsShortArray = resources.getStringArray(R.array.months_array_short)

        val calendar = Calendar.getInstance()
        currentMonthIndex = calendar.get(Calendar.MONTH)
        attendanceViewModel.updateMonth(currentMonthIndex + 1)
        attendanceViewModel.updateYear(currentYear)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAttendance2Binding.inflate(layoutInflater,container,false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attendanceViewModel.selectedMonth.observe(viewLifecycleOwner) { month ->
            currentMonthIndex = month - 1
            updateMonthDisplay()
        }

        attendanceViewModel.selectedYear.observe(viewLifecycleOwner) { year ->
            selectedYear = year
            binding.buttonYear.text = selectedYear.toString()
        }

        val tabLayoutAdapter = AttendanceTabAdapter(requireActivity())
        val viewPagerTab : ViewPager2 =binding!!.vpTabAttendance
        viewPagerTab.adapter = tabLayoutAdapter
        val tabs : TabLayout = binding!!.tabLayoutAttendance

        val titleTab = listOf("Attendance","Submission")
        TabLayoutMediator(tabs,viewPagerTab){tab,ps ->
            tab.text = titleTab[ps]
        }.attach()

        updateMonthDisplay()

        binding.btnPreviousMonth.setOnClickListener {
            currentMonthIndex = if (currentMonthIndex > 0) currentMonthIndex - 1 else 11
            updateMonthDisplay()

            attendanceViewModel.updateMonth(currentMonthIndex + 1)
        }

        binding.btnNextMonth.setOnClickListener {
            currentMonthIndex = if (currentMonthIndex < 11) currentMonthIndex + 1 else 0
            updateMonthDisplay()

            attendanceViewModel.updateMonth(currentMonthIndex + 1)
        }

        val yearButton = binding.buttonYear
        binding.buttonYear.setOnClickListener {
            showYearPickerDialog(yearButton)
        }
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

                attendanceViewModel.updateYear(selectedYear)

                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    private fun updateMonthDisplay() {
        binding.tvSelectedMonth.text = monthsArray[currentMonthIndex]
        binding.tvPreviousMonth.text = if (currentMonthIndex > 0) monthsShortArray[currentMonthIndex - 1] else monthsShortArray[11]
        binding.tvNextMonth.text = if (currentMonthIndex < 11) monthsShortArray[currentMonthIndex + 1] else monthsShortArray[0]


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Attendance2Fragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}