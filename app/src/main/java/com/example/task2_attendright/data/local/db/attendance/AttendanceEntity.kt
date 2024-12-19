package com.example.task2_attendright.data.local.db.attendance

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendances")
data class AttendanceEntity(
    @PrimaryKey val attendanceId: String,
    val userId: String,
    val date: String, // Format "yyyy-MM-dd"
    val clockInTime: String?, // Jam clock-in
    val clockOutTime: String?, // Jam clock-out
    val latitude: Double?, // Optional
    val longitude: Double?, // Optional
    val address: String?, // Optional
    val attendanceResult: String // Enum dalam bentuk String
)
