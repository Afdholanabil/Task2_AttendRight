package com.example.task2_attendright.domain.repository

import com.example.task2_attendright.domain.model.Attendance

interface AttendanceRepository {
    suspend fun saveAttendance(attendance: Attendance)
    suspend fun getAttendanceByDate(userId: String, date: String): Attendance?
    suspend fun getAttendanceByMonth(userId: String, year: Int, month: Int): List<Attendance>

    // Tambahkan fungsi baru
    suspend fun clockIn(attendance: Attendance)
    suspend fun clockOut(attendance: Attendance)
}
