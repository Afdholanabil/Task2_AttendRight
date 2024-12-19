package com.example.task2_attendright.domain.model

import com.example.task2_attendright.domain.enums.AttendanceResult

data class Attendance(
    val attendanceId: String,
    val userId: String,
    val date: String, // Format "yyyy-MM-dd"
    val clockInTime: String?,
    val clockOutTime: String?,
    val latitude: Double?,
    val longitude: Double?,
    val address: String?,
    val attendanceResult: AttendanceResult
)
