package com.example.task2_attendright.domain.usecase.attendance

import com.example.task2_attendright.domain.model.Attendance
import com.example.task2_attendright.domain.repository.AttendanceRepository

class ClockInUseCase(
    private val attendanceRepository: AttendanceRepository
) {
    suspend operator fun invoke(attendance: Attendance) {
        // Validasi data, misal: cek jika user belum clock in hari ini
        attendanceRepository.clockIn(attendance)
    }
}