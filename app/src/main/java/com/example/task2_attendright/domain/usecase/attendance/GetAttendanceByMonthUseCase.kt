package com.example.task2_attendright.domain.usecase.attendance

import com.example.task2_attendright.domain.model.Attendance
import com.example.task2_attendright.domain.repository.AttendanceRepository

class GetAttendanceByMonthUseCase(
    private val attendanceRepository: AttendanceRepository
) {
    suspend operator fun invoke(userId: String, year: Int, month: Int): List<Attendance> {
        return attendanceRepository.getAttendanceByMonth(userId, year, month)
    }
}