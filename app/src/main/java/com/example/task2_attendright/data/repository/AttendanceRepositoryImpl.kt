package com.example.task2_attendright.data.repository

import com.example.task2_attendright.data.local.localdatasource.AttendanceLocalDataSource
import com.example.task2_attendright.domain.model.Attendance
import com.example.task2_attendright.domain.repository.AttendanceRepository

class AttendanceRepositoryImpl(
    private val localDataSource: AttendanceLocalDataSource
) : AttendanceRepository {

    override suspend fun clockIn(attendance: Attendance) {
        localDataSource.saveAttendance(attendance)
    }

    override suspend fun clockOut(attendance: Attendance) {
        localDataSource.saveAttendance(attendance)
    }

    override suspend fun getAttendanceByMonth(userId: String, year: Int, month: Int): List<Attendance> {
        return localDataSource.getAttendancesByMonth(userId, year, month)
    }
}