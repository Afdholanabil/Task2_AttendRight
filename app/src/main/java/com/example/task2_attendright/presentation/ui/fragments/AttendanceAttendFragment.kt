package com.example.task2_attendright.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.AttendanceHoliday
import com.example.task2_attendright.data.local.AttendanceItemCount
import com.example.task2_attendright.data.local.AttendanceRecord
import com.example.task2_attendright.databinding.FragmentAttendanceAttendBinding
import com.example.task2_attendright.presentation.ui.adapter.AttendanceCountDaysAdapter
import com.example.task2_attendright.presentation.ui.adapter.AttendanceRecordAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class AttendanceAttendFragment : Fragment() {

    private var _binding : FragmentAttendanceAttendBinding? = null
    private val bindings get() = _binding
    private lateinit var attendanceRecordAdapter: AttendanceRecordAdapter

    private var currentMonth: Int = Calendar.getInstance().get(Calendar.MONTH)
    private var currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttendanceAttendBinding.inflate(layoutInflater,container,false)

        return bindings!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = bindings!!.rvCountAttendance
        val items = listOf(
            AttendanceItemCount("Late", 1, "Day"),
            AttendanceItemCount("Early Clock Out", 2, "Day"),
            AttendanceItemCount("No Clock In/Out", 3, "Day"),
            AttendanceItemCount("Attendance", 5, "Day"),
            AttendanceItemCount("Alpha", 0, "Day"),
            AttendanceItemCount("Leave", 0, "Day")
        )

        rv.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = AttendanceCountDaysAdapter(items)


        attendanceRecordAdapter = AttendanceRecordAdapter()
        bindings!!.rvRecordAttendance.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = attendanceRecordAdapter
        }

        updateDataForMonthYear(currentMonth,currentYear)
    }

    fun updateDataForMonthYear(month: Int, year: Int) {
        currentMonth = month
        currentYear = year
        val updateData = generateAttendanceData(currentMonth, currentYear)

        Log.d("AttendanceAttendFragment", "Updating data for month: $month, year: $year with ${updateData.size} records")
        attendanceRecordAdapter.submitList(updateData) {
            bindings?.rvRecordAttendance?.scrollToPosition(0)
        }
    }


    private fun generateAttendanceData(month: Int, year: Int): List<AttendanceRecord> {
        val attendanceList = mutableListOf<AttendanceRecord>()

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())

        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        val holidays = listOf(
            AttendanceHoliday("Sunday, 01 Nov 2024", "Weekend Holiday"),
            AttendanceHoliday("Sunday, 29 Nov 2024", "Libur Hari Raya Idul Fitri 2024")
        )

        for (day in 1..daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val dateString = dateFormat.format(calendar.time)

            val holiday = holidays.find { it.date == dateString }
            if (holiday != null) {
                attendanceList.add(AttendanceRecord(holiday.date, holiday.description, ""))
            } else {
                attendanceList.add(AttendanceRecord(dateString))
            }
        }

        Log.d("AttendanceAttendFragment", "Generated ${attendanceList.size} attendance records for month: $month, year: $year")
        return attendanceList
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            AttendanceAttendFragment()
    }
}