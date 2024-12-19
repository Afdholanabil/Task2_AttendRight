package com.example.task2_attendright.presentation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.AttendanceHoliday
import com.example.task2_attendright.data.local.AttendanceItemCount
import com.example.task2_attendright.data.local.AttendanceRecord
import com.example.task2_attendright.data.repository.AttendanceRepositoryImpl
import com.example.task2_attendright.databinding.FragmentAttendanceAttendBinding
import com.example.task2_attendright.presentation.ui.adapter.AttendanceCountDaysAdapter
import com.example.task2_attendright.presentation.ui.adapter.AttendanceRecordAdapter
import com.example.task2_attendright.presentation.ui.activities.MainActivity
import com.example.task2_attendright.presentation.viewmodel.AttendanceViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AttendanceAttendFragment : Fragment() {

    private var _binding : FragmentAttendanceAttendBinding? = null
    private val bindings get() = _binding!!
    private lateinit var attendanceRecordAdapter: AttendanceRecordAdapter
    private lateinit var attendanceViewModel: AttendanceViewModel

    private var selectedMonth: Int = Calendar.getInstance().get(Calendar.MONTH) + 1
    private var selectedYear: Int = Calendar.getInstance().get(Calendar.YEAR)

    // Tambahkan repository
    private val attendanceRepository by lazy {
        AttendanceRepositoryImpl(MainActivity.database.attendanceDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attendanceViewModel = ViewModelProvider(requireActivity()).get(AttendanceViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttendanceAttendBinding.inflate(layoutInflater,container,false)
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = bindings.rvCountAttendance
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
        bindings.rvRecordAttendance.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = attendanceRecordAdapter
        }

        attendanceViewModel.selectedMonth.observe(viewLifecycleOwner) { month ->
            selectedMonth = month
            updateDataForMonthYear(selectedMonth, selectedYear)
        }

        attendanceViewModel.selectedYear.observe(viewLifecycleOwner) { year ->
            selectedYear = year
            updateDataForMonthYear(selectedMonth, selectedYear)
        }

        updateDataForMonthYear(selectedMonth,selectedYear)
    }

    fun updateDataForMonthYear(month: Int, year: Int) {
        val prefs = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val loggedUserId = prefs.getString("loggedUserId", "") ?: return

        CoroutineScope(Dispatchers.IO).launch {
            val records = attendanceRepository.getAttendanceByMonth(loggedUserId, year, month)
            val attendanceRecords = records.map {
                AttendanceRecord(
                    date = convertDateToReadable(it.date),
                    clockIn = it.clockInTime ?: "--:--:--",
                    clockOut = it.clockOutTime ?: "--:--:--"
                )
            }
            withContext(Dispatchers.Main) {
                attendanceRecordAdapter.submitList(attendanceRecords) {
                    bindings.rvRecordAttendance.scrollToPosition(0)
                }
            }
        }
    }

    private fun convertDateToReadable(date: String): String {
        val sdfSource = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val sdfTarget = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
        val d = sdfSource.parse(date)
        return sdfTarget.format(d!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = AttendanceAttendFragment()
    }
}
