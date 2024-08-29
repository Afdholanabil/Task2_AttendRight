package com.example.task2_attendright.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.AttendanceItemCount
import com.example.task2_attendright.data.local.AttendanceRecord
import com.example.task2_attendright.databinding.FragmentAttendanceAttendBinding
import com.example.task2_attendright.presentation.ui.adapter.AttendanceCountDaysAdapter
import com.example.task2_attendright.presentation.ui.adapter.AttendanceRecordAdapter


class AttendanceAttendFragment : Fragment() {

    private var _binding : FragmentAttendanceAttendBinding? = null
    private val bindings get() = _binding
    private lateinit var attendanceRecordAdapter: AttendanceRecordAdapter

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

        val attendList  = listOf(
            AttendanceRecord("Sunday, 01 Nov 2024", "Weekend Holiday", ""),
            AttendanceRecord("Monday, 02 Nov 2024", "--:--", "--:--"),

            )

        attendanceRecordAdapter = AttendanceRecordAdapter(attendList)
        bindings!!.rvRecordAttendance.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = attendanceRecordAdapter
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AttendanceAttendFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}