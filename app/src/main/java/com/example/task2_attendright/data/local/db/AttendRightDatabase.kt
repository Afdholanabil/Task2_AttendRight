package com.example.task2_attendright.data.local.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task2_attendright.data.local.db.attendance.AttendanceDao
import com.example.task2_attendright.data.local.db.attendance.AttendanceEntity
import com.example.task2_attendright.data.local.db.user.UserDao
import com.example.task2_attendright.data.local.db.user.UserEntity

@Database(
    entities = [UserEntity::class, AttendanceEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AttendRightDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun attendanceDao(): AttendanceDao
}