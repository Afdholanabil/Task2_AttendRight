// final_clock_in_activity.kt
package com.example.task2_attendright.presentation.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.task2_attendright.data.local.datastore.AuthorityDataStore
import com.example.task2_attendright.data.repository.AttendanceRepositoryImpl
import com.example.task2_attendright.databinding.ActivityFinalClockInBinding
import com.example.task2_attendright.domain.enums.AttendanceResult
import com.example.task2_attendright.domain.model.Attendance
import com.example.task2_attendright.util.AttendanceStatusEvaluator
import com.example.task2_attendright.util.getCorrectlyOrientedBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
// Sesuaikan import:


class final_clock_in_activity : AppCompatActivity() {
    private lateinit var binding: ActivityFinalClockInBinding
    private lateinit var authorityDataStore: AuthorityDataStore
    private lateinit var attendanceRepository: AttendanceRepositoryImpl
    private var currentTime = getCurrentTimeStamp()
    private var currentDate = getCurrentDate()
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinalClockInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authorityDataStore = AuthorityDataStore(this)
        attendanceRepository = AttendanceRepositoryImpl(MainActivity.database.attendanceDao())

        val prefs = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        userId = prefs.getString("loggedUserId", "") ?: ""

        binding.txtClockNow.text = currentTime
        binding.txtCurrentDay.text = currentDate

        lifecycleScope.launch {
            authorityDataStore.isPermissionGranted().collect { granted ->
                if (!granted) {
                    // Jika izin belum diberikan, kembali ke AuthorityCheckActivity
                    val intent = Intent(this@final_clock_in_activity, AuthorityCheckActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Lanjutkan dengan logika Clock In atau Clock Out
                    setupClockInOrOut()
                }
            }
        }

        binding.btnMapsBack.setOnClickListener { onBackPressed() }
    }

    private fun setupClockInOrOut() {
        val locationText = intent.getStringExtra("address2")
        binding.addressText.text = locationText

        val imagePath = intent.getStringExtra("imagePath")
        val imageBitmap = imagePath?.let { getCorrectlyOrientedBitmap(it) }
        binding.imageView.setImageBitmap(imageBitmap)

        CoroutineScope(Dispatchers.IO).launch {
            val attendanceToday = attendanceRepository.getAttendanceByDate(userId, getDateForDB())
            runOnUiThread {
                if (attendanceToday == null) {
                    binding.btnMapsNext.setOnClickListener { saveClockInData(locationText) }
                } else {
                    binding.btnMapsNext.setOnClickListener { saveClockOutData(locationText, attendanceToday) }
                }
            }
        }
    }

    private fun saveClockInData(address: String?) {
        val attendance = Attendance(
            attendanceId = "$userId-${getDateForDB()}",
            userId = userId,
            date = getDateForDB(),
            clockInTime = currentTime,
            clockOutTime = null,
            latitude = null,
            longitude = null,
            address = address,
            attendanceResult = AttendanceResult.ATTENDANCE
        )
        CoroutineScope(Dispatchers.IO).launch {
            attendanceRepository.saveAttendance(attendance)
            navigateToSuccess("Clock In Berhasil!")
        }
    }

    private fun saveClockOutData(address: String?, oldAttendance: Attendance) {
        val updatedAttendance = oldAttendance.copy(
            clockOutTime = currentTime,
            attendanceResult = AttendanceResult.ATTENDANCE,
            address = address ?: oldAttendance.address
        )
        CoroutineScope(Dispatchers.IO).launch {
            attendanceRepository.clockOut(updatedAttendance)
            navigateToSuccess("Clock Out Berhasil!")
        }
    }

    private fun navigateToSuccess(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, success_information_clock_in::class.java)
            intent.putExtra("date", currentDate)
            intent.putExtra("time", currentTime)
            startActivity(intent)
            finish()
        }
    }

    private fun getCurrentTimeStamp(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale("id", "ID"))
        return dateFormat.format(Date())
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale("id", "ID"))
        return dateFormat.format(Date())
    }

    private fun getDateForDB(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}

