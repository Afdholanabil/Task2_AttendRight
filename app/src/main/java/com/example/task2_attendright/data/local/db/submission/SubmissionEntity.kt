package com.example.task2_attendright.data.local.db.submission

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "submissions")
data class SubmissionEntity(
    @PrimaryKey(autoGenerate = true) val submissionId: Int = 0,
    val type: String, // "WORK", "LEAVE", "SHIFT", "ATTENDANCE"
    val title: String?,
    val startDate: String?, // Untuk leave request atau shift change atau attendance yang punya date range
    val endDate: String?,   // Jika leave, shift mungkin punya range waktu (optional), jika single date maka boleh diisi sama
    val time: String?,      // misalnya clock_in atau clock_out attendance, shift juga mungkin butuh jam tertentu
    val description: String?,
    val imageUri: String?,  // path atau URI ke image jika ada
    val status: String      // "Pending", "Approved", "Declined"
)
