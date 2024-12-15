package com.example.task2_attendright.domain.repository

import com.example.task2_attendright.domain.model.Attendance

interface AttendanceRepository {
    suspend fun clockIn(attendance: Attendance)
    suspend fun clockOut(attendance: Attendance)
    suspend fun getAttendanceByMonth(userId: String, year: Int, month: Int): List<Attendance>
}