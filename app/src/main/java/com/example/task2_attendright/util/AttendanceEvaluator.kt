package com.example.task2_attendright.util


import com.example.task2_attendright.domain.enums.AttendanceResult
import java.text.SimpleDateFormat
import java.util.*

object AttendanceStatusEvaluator {
    fun evaluateStatus(clockInTime: String?, clockOutTime: String?): AttendanceResult {
        if (clockInTime == null || clockOutTime == null) {
            return AttendanceResult.NO_CLOCK_IN_OUT
        }

        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val nineAM = sdf.parse("09:00:00")!!
        val fivePM = sdf.parse("17:00:00")!!

        val inTime = sdf.parse(clockInTime)
        val outTime = sdf.parse(clockOutTime)

        return when {
            inTime.after(nineAM) -> AttendanceResult.LATE
            outTime.before(fivePM) -> AttendanceResult.EARLY_CLOCK_OUT
            else -> AttendanceResult.ATTENDANCE
        }
    }
}
