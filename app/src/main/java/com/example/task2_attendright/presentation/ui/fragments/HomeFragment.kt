package com.example.task2_attendright.presentation.ui.fragments

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.MeetingToday
import com.example.task2_attendright.data.local.TodayTask
import com.example.task2_attendright.databinding.FragmentHomeBinding
import com.example.task2_attendright.presentation.ui.activities.AuthorityCheckActivity
import com.example.task2_attendright.presentation.ui.adapter.MeetingTodayAdapter
import com.example.task2_attendright.presentation.ui.adapter.TodayTasksAdapter
import com.example.task2_attendright.presentation.ui.animation.AnimationUtil
import com.example.task2_attendright.presentation.ui.customview.MyCircularProgress
import com.example.task2_attendright.util.DailyCheckReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        val recyclerViewTodayTask = binding!!.rvTodayTasks
        val recyclerViewTodayMeeting = binding!!.rvMeetingToday
        recyclerViewTodayTask.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        recyclerViewTodayMeeting.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)

        binding!!.rvTodayTasks.isNestedScrollingEnabled = false
        binding!!.rvMeetingToday.isNestedScrollingEnabled = false
        val dummyTasks = listOf(
            TodayTask("08:00 WIB", "Daily Standup Meeting",20),
            TodayTask("10:00 WIB", "Design Review",10),
            TodayTask("13:00 WIB", "Development Sprint",69)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetOnNewDay()
        arguments?.let {
            val dateArg = it.getString(ARG_DATE)
            val timeArg = it.getString(ARG_TIME)
            handleIncomingData(dateArg,timeArg)
        }

        scheduleDailyCheck(requireContext())
        checkAttendanceStatus()
        binding!!.btnClockIn.setOnClickListener {
            onClockIn()
        }
        binding!!.btnClockOut.setOnClickListener {
            onClockOut()
        }

        val (currentDate, currentTime) = setDateTimeDay()
        binding!!.tvClockinOutDate.text = currentDate
        startRealTimeClock()

    }

    private fun startRealTimeClock() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                updateTime()
                delay(2000)
            }
        }
    }

    private fun updateTime() {
        val calendar = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = timeFormat.format(calendar.time)

        binding!!.tvClockinOutTimeHome.text = "$currentTime WIB"
    }

    private fun scheduleDailyCheck(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager
        val intent = Intent(context, DailyCheckReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setRepeating(
            android.app.AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            android.app.AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun setDateTimeDay() : Pair<String, String>{
        val calendar = Calendar.getInstance()

        val dateFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(calendar.time)

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = timeFormat.format(calendar.time)

        return Pair(currentDate, currentTime)

    }

    private fun checkAttendanceStatus() {
        val sharedPreferences = requireContext().getSharedPreferences("AttendancePrefs", Context.MODE_PRIVATE)
        val hasClockedIn = sharedPreferences.getBoolean("hasClockedIn", false)
        val hasClockedOut = sharedPreferences.getBoolean("hasClockedOut", false)
        val clockInTime = sharedPreferences.getString("clockInTime", "-- : --")
        val clockOutTime = sharedPreferences.getString("clockOutTime", "-- : --")

        binding!!.tvClockInAttendanceTime.text = clockInTime
        binding!!.tvClockOutAttendanceTime.text = clockOutTime

        binding!!.btnClockIn.isEnabled = !hasClockedIn
        binding!!.btnClockOut.isEnabled = hasClockedIn && !hasClockedOut
    }

    private fun onClockIn() {
        val sharedPreferences = requireContext().getSharedPreferences("AttendancePrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("hasClockedIn", true).apply()

        binding!!.btnClockIn.isEnabled = false
        binding!!.btnClockOut.isEnabled = true

        val intent = Intent(requireContext(), AuthorityCheckActivity::class.java)
        AnimationUtil.startFragmentWithSlideAnimation(requireActivity(), intent)

        Toast.makeText(requireContext(), "Clock In berhasil", Toast.LENGTH_SHORT).show()
    }

    private fun onClockOut() {
        val sharedPreferences = requireContext().getSharedPreferences("AttendancePrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("hasClockedOut", true).apply()

        val intent = Intent(requireContext(), AuthorityCheckActivity::class.java)
        AnimationUtil.startFragmentWithSlideAnimation(requireActivity(), intent)

        binding!!.btnClockOut.isEnabled = false
        Toast.makeText(requireContext(), "Clock Out berhasil", Toast.LENGTH_SHORT).show()
    }


    private fun saveClockInTime(time: String) {
        val sharedPreferences = requireContext().getSharedPreferences("AttendancePrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("clockInTime", time).apply()
        updateClockInTime(time)
    }

    private fun saveClockOutTime(time: String) {
        val sharedPreferences = requireContext().getSharedPreferences("AttendancePrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("clockOutTime", time).apply()
        updateClockOutTime(time)
    }

    private fun updateClockInTime(time: String) {
        binding!!.tvClockInAttendanceTime.text = time
    }

    private fun updateClockOutTime(time: String) {
        binding!!.tvClockOutAttendanceTime.text = time
    }

    private fun handleIncomingData(date: String?, time: String?) {
        val sharedPreferences = requireContext().getSharedPreferences("AttendancePrefs", Context.MODE_PRIVATE)
        val hasClockedIn = sharedPreferences.getBoolean("hasClockedIn", false)
        val hasClockedOut = sharedPreferences.getBoolean("hasClockedOut", false)

        if (!hasClockedOut && !time.isNullOrEmpty()) {
            saveClockInTime(time)
        } else if (hasClockedIn && !time.isNullOrEmpty()) {
            saveClockOutTime(time)
        }
    }


    private fun resetOnNewDay() {
        val sharedPreferences = requireContext().getSharedPreferences("AttendancePrefs", Context.MODE_PRIVATE)
        val lastCheckedDate = sharedPreferences.getString("lastCheckedDate", "")

        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)


        if (currentDate != lastCheckedDate) {

            sharedPreferences.edit()
                .putBoolean("hasClockedIn", false)
                .putBoolean("hasClockedOut", false)
                .putString("clockInTime", "-- : --")
                .putString("clockOutTime", "-- : --")
                .putString("lastCheckedDate", currentDate)
                .apply()

            binding!!.btnClockIn.isEnabled = true
            binding!!.btnClockOut.isEnabled = false
            binding!!.tvClockInAttendanceTime.text = "-- : --"
            binding!!.tvClockOutAttendanceTime.text = "-- : --"
        }
    }



    companion object {

        @JvmStatic
        fun newInstance(date: String?, time: String?) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DATE, date)
                    putString(ARG_TIME, time)
                }
            }

        private const val ARG_DATE = "date"
        private const val ARG_TIME = "time"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job?.cancel()
    }
}