package com.example.task2_attendright.domain.model

import com.example.task2_attendright.domain.enums.AttendanceResult
import com.example.task2_attendright.domain.enums.AttendenceType

data class Attendance(
    val attendanceId: String,
    val userId: String, // relasi ke user
    val type: AttendenceType, // CLOCK_IN / CLOCK_OUT
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val second: Int,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val attendanceResult: AttendanceResult
) {

}