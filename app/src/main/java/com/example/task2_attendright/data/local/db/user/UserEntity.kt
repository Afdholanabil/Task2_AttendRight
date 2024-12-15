package com.example.task2_attendright.data.local.db.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val userId: String,
    val name: String,
    val role: String,
    val gender: String,
    val birthDate: String,
    val phone: String,
    val religion: String
)