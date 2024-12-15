package com.example.task2_attendright.data.local.db.attendance

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance_table")
data class AttendanceEntity(
    @PrimaryKey val attendanceId: String,
    val userId: String,
    val type: String,  // "CLOCK_IN" / "CLOCK_OUT"
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val second: Int,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val attendanceResult: String // "LATE", "ATTENDANCE", dsb.
)