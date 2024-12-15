package com.example.task2_attendright.domain.usecase.attendance

import com.example.task2_attendright.domain.model.Attendance
import com.example.task2_attendright.domain.repository.AttendanceRepository

class ClockOutUseCase(
    private val attendanceRepository: AttendanceRepository
) {
    suspend operator fun invoke(attendance: Attendance) {
        // Validasi, misal: cek user sudah clock in sebelumnya, dsb.
        attendanceRepository.clockOut(attendance)
    }
}