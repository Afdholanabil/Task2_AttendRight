package com.example.task2_attendright.domain.repository

import com.example.task2_attendright.domain.model.Submission

interface SubmissionRepository {
    suspend fun saveSubmission(submission: Submission)
    suspend fun getSubmissionById(id: Int): Submission?
    suspend fun getAllSubmissions(): List<Submission>
    suspend fun deleteSubmissionById(id: Int)
}
