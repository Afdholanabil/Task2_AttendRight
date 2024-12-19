package com.example.task2_attendright.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task2_attendright.data.local.db.attendance.AttendanceEntity
import com.example.task2_attendright.data.local.db.attendance.AttendanceDao
import com.example.task2_attendright.data.local.db.user.UserDao
import com.example.task2_attendright.data.local.db.user.UserEntity
import com.example.task2_attendright.data.local.db.submission.SubmissionEntity
import com.example.task2_attendright.data.local.db.submission.SubmissionDao

@Database(entities = [UserEntity::class, AttendanceEntity::class, SubmissionEntity::class],
    version = 1,
    exportSchema = false)
abstract class AttendRightDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun submissionDao(): SubmissionDao
}
