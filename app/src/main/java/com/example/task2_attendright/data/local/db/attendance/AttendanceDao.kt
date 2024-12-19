package com.example.task2_attendright.data.local.db.attendance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceEntity)

    @Query("SELECT * FROM attendances WHERE userId = :userId AND substr(date, 1, 7) = :yearMonth")
    suspend fun getAttendanceByMonth(userId: String, yearMonth: String): List<AttendanceEntity>

    @Query("SELECT * FROM attendances WHERE userId = :userId AND date = :date LIMIT 1")
    suspend fun getAttendanceByDate(userId: String, date: String): AttendanceEntity?

    @Query("UPDATE attendances SET clockOutTime = :clockOutTime, attendanceResult = :result WHERE attendanceId = :attendanceId")
    suspend fun updateClockOut(attendanceId: String, clockOutTime: String, result: String)
}

