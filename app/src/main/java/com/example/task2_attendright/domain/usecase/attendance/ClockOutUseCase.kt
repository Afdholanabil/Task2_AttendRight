package com.example.task2_attendright.domain.usecase.attendance

import com.example.task2_attendright.domain.model.Attendance
import com.example.task2_attendright.domain.repository.AttendanceRepository

class ClockOutUseCase(
    private val attendanceRepository: AttendanceRepository
) {
    suspend operator fun invoke(attendance: Attendance) {
        // validasi
        attendanceRepository.saveAttendance(attendance)
    }
}