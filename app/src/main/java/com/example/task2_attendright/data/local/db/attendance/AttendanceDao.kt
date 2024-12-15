package com.example.task2_attendright.data.local.db.attendance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceEntity)

    @Query("""
        SELECT * FROM attendance_table 
        WHERE userId = :userId AND year = :year AND month = :month
        ORDER BY day ASC, hour ASC, minute ASC, second ASC
    """)
    suspend fun getAttendanceByMonth(userId: String, year: Int, month: Int): List<AttendanceEntity>
}