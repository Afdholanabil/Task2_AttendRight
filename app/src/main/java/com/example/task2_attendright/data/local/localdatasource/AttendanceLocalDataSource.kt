package com.example.task2_attendright.data.local.localdatasource

import com.example.task2_attendright.data.local.db.attendance.AttendanceEntity
import com.example.task2_attendright.domain.enums.AttendanceResult
import com.example.task2_attendright.domain.enums.AttendenceType
import com.example.task2_attendright.domain.model.Attendance

interface AttendanceLocalDataSource {
    suspend fun saveAttendance(attendance: Attendance)
    suspend fun getAttendancesByMonth(userId: String, year: Int, month: Int): List<Attendance>
}

fun Attendance.toEntity(): AttendanceEntity {
    return AttendanceEntity(
        attendanceId = this.attendanceId,
        userId = this.userId,
        type = this.type.name,
        year = this.year,
        month = this.month,
        day = this.day,
        hour = this.hour,
        minute = this.minute,
        second = this.second,
        latitude = this.latitude,
        longitude = this.longitude,
        address = this.address,
        attendanceResult = this.attendanceResult.name
    )
}

fun AttendanceEntity.toDomain(): Attendance {
    return Attendance(
        attendanceId = this.attendanceId,
        userId = this.userId,
        type = AttendenceType.valueOf(this.type),
        year = this.year,
        month = this.month,
        day = this.day,
        hour = this.hour,
        minute = this.minute,
        second = this.second,
        latitude = this.latitude,
        longitude = this.longitude,
        address = this.address,
        attendanceResult = AttendanceResult.valueOf(this.attendanceResult)
    )
}
