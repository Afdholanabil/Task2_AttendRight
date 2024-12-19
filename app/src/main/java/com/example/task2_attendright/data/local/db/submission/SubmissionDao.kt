package com.example.task2_attendright.data.local.db.submission

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SubmissionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubmission(submission: SubmissionEntity)

    @Query("SELECT * FROM submissions WHERE submissionId = :id LIMIT 1")
    suspend fun getSubmissionById(id: Int): SubmissionEntity?

    @Query("SELECT * FROM submissions")
    suspend fun getAllSubmissions(): List<SubmissionEntity>

    @Query("DELETE FROM submissions WHERE submissionId = :id")
    suspend fun deleteSubmissionById(id: Int)
}
