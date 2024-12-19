package com.example.task2_attendright.data.local.localdatasource

import com.example.task2_attendright.data.local.db.attendance.AttendanceDao
import com.example.task2_attendright.domain.model.Attendance

class AttendanceLocalDataSourceImpl(
    private val attendanceDao: AttendanceDao
) : AttendanceLocalDataSource {

    override suspend fun saveAttendance(attendance: Attendance) {
        attendanceDao.insertAttendance(attendance.toEntity())
    }

    override suspend fun getAttendancesByMonth(userId: String, year: Int, month: Int): List<Attendance> {
        val yearMonth = String.format("%04d-%02d", year, month) // Format ke "yyyy-MM"
        return attendanceDao.getAttendanceByMonth(userId, yearMonth)
            .map { it.toDomain() }
    }
}
