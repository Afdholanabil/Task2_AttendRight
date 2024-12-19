package com.example.task2_attendright.data.repository

import com.example.task2_attendright.data.local.db.attendance.AttendanceDao
import com.example.task2_attendright.data.local.db.toDomain
import com.example.task2_attendright.data.local.db.toEntity

import com.example.task2_attendright.domain.model.Attendance
import com.example.task2_attendright.domain.repository.AttendanceRepository
import java.text.SimpleDateFormat
import java.util.Locale

class AttendanceRepositoryImpl(
    private val attendanceDao: AttendanceDao
) : AttendanceRepository {

    override suspend fun saveAttendance(attendance: Attendance) {
        attendanceDao.insertAttendance(attendance.toEntity())
    }

    override suspend fun getAttendanceByDate(userId: String, date: String): Attendance? {
        val entity = attendanceDao.getAttendanceByDate(userId, date)
        return entity?.toDomain()
    }

    override suspend fun getAttendanceByMonth(userId: String, year: Int, month: Int): List<Attendance> {
        val yearMonth = String.format("%04d-%02d", year, month)
        return attendanceDao.getAttendanceByMonth(userId, yearMonth).map { it.toDomain() }
    }

    override suspend fun clockIn(attendance: Attendance) {
        // Misalnya logic clockIn sama seperti saveAttendance tapi dengan validasi
        attendanceDao.insertAttendance(attendance.toEntity())
    }

    override suspend fun clockOut(attendance: Attendance) {
        attendanceDao.updateClockOut(
            attendanceId = attendance.attendanceId,
            clockOutTime = attendance.clockOutTime ?: "",
            result = attendance.attendanceResult.name
        )
    }



}

