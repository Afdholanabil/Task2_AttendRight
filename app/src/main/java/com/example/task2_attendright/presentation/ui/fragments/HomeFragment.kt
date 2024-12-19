package com.example.task2_attendright.presentation.ui.fragments

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.task2_attendright.R
import com.example.task2_attendright.data.local.MeetingToday
import com.example.task2_attendright.data.local.TodayTask
import com.example.task2_attendright.data.local.datastore.AttendanceDataStore
import com.example.task2_attendright.data.local.datastore.AuthorityDataStore
import com.example.task2_attendright.data.repository.AttendanceRepositoryImpl
import com.example.task2_attendright.data.repository.UserRepositoryImpl
import com.example.task2_attendright.databinding.FragmentHomeBinding
import com.example.task2_attendright.presentation.ui.activities.AuthorityCheckActivity
import com.example.task2_attendright.presentation.ui.activities.DashboardActivity
import com.example.task2_attendright.presentation.ui.activities.MainActivity
import com.example.task2_attendright.presentation.ui.activities.final_clock_in_activity
import com.example.task2_attendright.presentation.ui.activities.location_activity_osm
import com.example.task2_attendright.presentation.ui.adapter.MeetingTodayAdapter
import com.example.task2_attendright.presentation.ui.adapter.TodayTasksAdapter
import com.example.task2_attendright.presentation.ui.animation.AnimationUtil
import com.example.task2_attendright.util.DailyCheckReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Pastikan job dideklarasikan
    private var job: Job? = null

    private val attendanceRepository by lazy {
        AttendanceRepositoryImpl(MainActivity.database.attendanceDao())
    }

    private val attendanceDataStore by lazy { AttendanceDataStore(requireContext()) }
    private val authorityDataStore by lazy { AuthorityDataStore(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        val recyclerViewTodayTask = binding.rvTodayTasks
        val recyclerViewTodayMeeting = binding.rvMeetingToday
        recyclerViewTodayTask.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        recyclerViewTodayMeeting.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)

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

        return binding.root
    }

    private fun loadUserData() {
        val prefs = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val loggedUserId = prefs.getString("loggedUserId", null) ?: return

        val userDao = MainActivity.database.userDao()
        val userRepository = UserRepositoryImpl(userDao)
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            val user = userRepository.getUserById(loggedUserId)
            user?.let {
                binding.tvNamaHome.text = it.name
                binding.tvRoleProfileHome.text = it.role
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserData()
        resetOnNewDay()
        scheduleDailyCheck(requireContext())

        // Observe attendance data store state (flow)
        observeAttendanceState()

        binding.btnClockIn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                authorityDataStore.isPermissionGranted().collect { granted ->
                    if (granted) {
                        val intent = Intent(requireContext(), location_activity_osm::class.java)
                        intent.putExtra("actionType", "clock_in")
                        startActivityForResult(intent, REQUEST_LOCATION_CLOCK_IN)
                    } else {
                        val intent = Intent(requireContext(), AuthorityCheckActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }

        binding.btnClockOut.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                authorityDataStore.isPermissionGranted().collect { granted ->
                    if (granted) {
                        val intent = Intent(requireContext(), location_activity_osm::class.java)
                        intent.putExtra("actionType", "clock_out")
                        startActivityForResult(intent, REQUEST_LOCATION_CLOCK_OUT)
                    } else {
                        val intent = Intent(requireContext(), AuthorityCheckActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }

        val (currentDate, _) = setDateTimeDay()
        binding.tvClockinOutDate.text = currentDate
        startRealTimeClock()

        binding.tvSeeMoreHomeMeeting.setOnClickListener {
            val intent = Intent(requireContext(), DashboardActivity::class.java)
            intent.putExtra("FRAGMENT_TO_OPEN", 3)
            startActivity(intent)
        }
        binding.tvSeeMoreHomeAttendance.setOnClickListener {
            val intent = Intent(requireContext(), DashboardActivity::class.java)
            intent.putExtra("FRAGMENT_TO_OPEN",2)
            startActivity(intent)
        }

        val ivProfile = binding.ivProfileHome
        Glide.with(requireContext()).load(R.drawable.image_26).circleCrop().into(ivProfile)
        ivProfile.setOnClickListener {
            val intent = Intent(requireContext(), DashboardActivity::class.java)
            intent.putExtra("FRAGMENT_TO_OPEN", 4)
            startActivity(intent)
        }

        // Load attendance dari DB
        loadAttendanceTimeFromDB()

        // Setelah load DB, atur tombol sesuai data store
        viewLifecycleOwner.lifecycleScope.launch {
            val status = attendanceDataStore.getClockStatus().first()
            binding.btnClockIn.isEnabled = !status.hasClockedIn
            binding.btnClockOut.isEnabled = status.hasClockedIn && !status.hasClockedOut
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val location = data.getStringExtra("address2") ?: ""
            val imagePath = data.getStringExtra("imagePath")

            when (requestCode) {
                REQUEST_LOCATION_CLOCK_IN -> navigateToFinalClockActivity("clock_in", location, imagePath)
                REQUEST_LOCATION_CLOCK_OUT -> navigateToFinalClockActivity("clock_out", location, imagePath)
            }
        }
    }

    private fun navigateToFinalClockActivity(actionType: String, address: String, imagePath: String?) {
        val intent = Intent(requireContext(), final_clock_in_activity::class.java)
        intent.putExtra("actionType", actionType)
        intent.putExtra("address2", address)
        intent.putExtra("imagePath", imagePath)
        startActivity(intent)
    }

    private fun observeAttendanceState() {
        viewLifecycleOwner.lifecycleScope.launch {
            attendanceDataStore.getClockStatus().collect { status ->
                // Update UI tombol sesuai dengan status data store
                binding.btnClockIn.isEnabled = !status.hasClockedIn
                binding.btnClockOut.isEnabled = status.hasClockedIn && !status.hasClockedOut
            }
        }
    }

    private fun loadAttendanceTimeFromDB() {
        val prefs = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val loggedUserId = prefs.getString("loggedUserId", null) ?: return
        val today = getTodayDateForDB()

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val attendanceToday = attendanceRepository.getAttendanceByDate(loggedUserId, today)
            withContext(Dispatchers.Main) {
                if (attendanceToday != null) {
                    binding.tvClockInAttendanceTime.text = attendanceToday.clockInTime ?: "-- : --"
                    binding.tvClockOutAttendanceTime.text = attendanceToday.clockOutTime ?: "-- : --"
                } else {
                    binding.tvClockInAttendanceTime.text = "-- : --"
                    binding.tvClockOutAttendanceTime.text = "-- : --"
                }
            }
        }
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun getTodayDateForDB(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun startRealTimeClock() {
        job = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
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
        binding.tvClockinOutTimeHome.text = "$currentTime WIB"
    }

    private fun scheduleDailyCheck(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager
        val intent = Intent(context, DailyCheckReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

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

    private fun resetOnNewDay() {
        // attendanceDataStore sudah handle reset otomatis jika currentDate berbeda
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
        private const val REQUEST_LOCATION_CLOCK_IN = 1001
        private const val REQUEST_LOCATION_CLOCK_OUT = 1002
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job?.cancel()
    }
}
