package com.example.task2_attendright.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DailyCheckReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val sharedPreferences = context.getSharedPreferences("AttendancePrefs", Context.MODE_PRIVATE)
        val lastCheckedDate = sharedPreferences.getString("lastCheckedDate", "")

        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().time)

        if (currentDate != lastCheckedDate) {

            sharedPreferences.edit()
                .putBoolean("hasClockedIn", false)
                .putBoolean("hasClockedOut", false)
                .putString("clockInTime", null)
                .putString("clockOutTime", null)
                .putString("lastCheckedDate", currentDate)
                .apply()

            enableClockInButton(context)
        } else {
            val hasClockIn = sharedPreferences.getBoolean("hasClockedIn", false)
            val hasClockOut = sharedPreferences.getBoolean("hasClockedOut", false)

            if (!hasClockIn) {
                enableClockInButton(context)
            } else if (!hasClockOut) {
                enableClockOutButton(context)
            } else {
                disableBothButtons(context)
            }
        }
    }

    private fun enableClockInButton(context: Context) {
        Toast.makeText(context, "Silakan Clock In", Toast.LENGTH_SHORT).show()
    }

    private fun enableClockOutButton(context: Context) {
        Toast.makeText(context, "Silakan Clock Out", Toast.LENGTH_SHORT).show()
    }

    private fun disableBothButtons(context: Context) {
        Toast.makeText(context, "Hari ini sudah selesai", Toast.LENGTH_SHORT).show()
    }
}