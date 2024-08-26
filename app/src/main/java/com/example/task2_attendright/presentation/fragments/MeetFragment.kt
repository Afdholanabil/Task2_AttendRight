package com.example.task2_attendright.presentation.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.task2_attendright.adapter.CalendarAdapter
import com.example.task2_attendright.adapter.MeetAdapter
import com.example.task2_attendright.data.local.MeetModel
import com.example.task2_attendright.databinding.FragmentMeetBinding
import com.example.task2_attendright.presentation.ui.add_meet_activity
import com.example.task2_attendright.presentation.ui.meet_detail_activity_offline
import com.example.task2_attendright.presentation.ui.meet_detail_activity_online
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MeetFragment : Fragment(), MeetAdapter.OnMeetClickListener {
    private var _binding: FragmentMeetBinding? = null
    private val binding get() = _binding!!

    // calendar
    private val lastDayInCalendar = Calendar.getInstance(Locale.ENGLISH)
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val currentDay = currentDate[Calendar.DAY_OF_MONTH]
    private val currentMonth = currentDate[Calendar.MONTH]
    private var selectedDay: Int = currentDay
    private var selectedMonth: Int = currentMonth
    private val dates = ArrayList<Date>()
    private val meetList = mutableListOf<MeetModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeetBinding.inflate(inflater, container, false)
        arguments?.let {

        }
        return _binding!!.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.calendarRecyclerView)
        lastDayInCalendar.add(Calendar.MONTH, 6)
        setUpCalendar()

        val meetAdapter = MeetAdapter(meetData, this)
        binding.rvDataMeet.layoutManager = LinearLayoutManager(context)
        binding.rvDataMeet.adapter = meetAdapter

        binding.btnAddMeet.setOnClickListener {
            val intent = Intent(activity, add_meet_activity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MeetFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun setUpCalendar(changeMonth: Calendar? = null) {
        binding.txtCurrentMonth.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        var currentPosition = 0
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        selectedDay =
            when {
                changeMonth != null -> changeMonth.getActualMinimum(Calendar.DAY_OF_MONTH)
                else -> currentDay
            }
        selectedMonth =
            when {
                changeMonth != null -> changeMonth[Calendar.MONTH]
                else -> currentMonth
            }

        while (dates.size < maxDaysInMonth) {
            if (monthCalendar[Calendar.DAY_OF_MONTH] == selectedDay)
                currentPosition = dates.size
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.calendarRecyclerView.layoutManager = layoutManager
        val calendarAdapter = CalendarAdapter(requireContext(), dates, currentDate, changeMonth)
        binding.calendarRecyclerView.adapter = calendarAdapter

        when {
            currentPosition > 2 -> binding.calendarRecyclerView.scrollToPosition(currentPosition - 3)
            maxDaysInMonth - currentPosition < 2 -> binding.calendarRecyclerView.scrollToPosition(
                currentPosition
            )

            else -> binding.calendarRecyclerView.scrollToPosition(currentPosition)
        }

        calendarAdapter.setOnItemClickListener(object : CalendarAdapter.OnItemClickListener,
            AdapterView.OnItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(position: Int) {
                val clickCalendar = Calendar.getInstance()
                clickCalendar.time = dates[position]
                selectedDay = clickCalendar[Calendar.DAY_OF_MONTH]
                val selectedDay =
                    SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(clickCalendar.time)
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMeetClick(meetModel: MeetModel) {
        if (meetModel.meetingModeMeet == "Online") {
            val intent = Intent(context, meet_detail_activity_online::class.java)
            intent.putExtra("meetDetails", meetModel)
            startActivity(intent)
        } else if (meetModel.meetingModeMeet == "Offline") {
            val intent = Intent(context, meet_detail_activity_offline::class.java)
            intent.putExtra("meetDetails", meetModel)
            startActivity(intent)
        }
    }

    val meetData = listOf(
        MeetModel(
            "Meeeting for World War 3",
            "Online",
            "meet.google.com/eer-iuyi-opk",
            "15:20 pm"
        ),
        MeetModel(
            "Meeeting for World War 3",
            "Offline",
            "Ruang Training Graha Pena, Surabaya, Jawa Timur.",
            "10:20 am"
        )
    )
}
