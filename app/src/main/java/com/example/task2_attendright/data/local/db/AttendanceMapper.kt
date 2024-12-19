package com.example.task2_attendright.data.local.db

import com.example.task2_attendright.data.local.db.attendance.AttendanceEntity
import com.example.task2_attendright.domain.enums.AttendanceResult
import com.example.task2_attendright.domain.model.Attendance

fun Attendance.toEntity(): AttendanceEntity {
    return AttendanceEntity(
        attendanceId = this.attendanceId,
        userId = this.userId,
        date = this.date, // Format "yyyy-MM-dd"
        clockInTime = this.clockInTime,
        clockOutTime = this.clockOutTime,
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
        date = this.date, // Format "yyyy-MM-dd"
        clockInTime = this.clockInTime,
        clockOutTime = this.clockOutTime,
        latitude = this.latitude,
        longitude = this.longitude,
        address = this.address,
        attendanceResult = AttendanceResult.valueOf(this.attendanceResult)
    )
}
