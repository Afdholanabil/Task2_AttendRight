package com.example.task2_attendright.data.local

data class AttendanceRecord(
    val date:String ,
    val clockIn : String = "-- : --",
    val clockOut: String = "-- : --"
)

data class AttendanceHoliday(
    val date: String,
    val description: String
)