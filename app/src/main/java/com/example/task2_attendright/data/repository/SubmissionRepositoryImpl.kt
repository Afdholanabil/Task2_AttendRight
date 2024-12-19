package com.example.task2_attendright.data.repository

import com.example.task2_attendright.data.local.db.submission.SubmissionDao
import com.example.task2_attendright.data.local.db.toDomain
import com.example.task2_attendright.data.local.db.toEntity
import com.example.task2_attendright.domain.model.Submission
import com.example.task2_attendright.domain.repository.SubmissionRepository


class SubmissionRepositoryImpl(
    private val submissionDao: SubmissionDao
): SubmissionRepository {
    override suspend fun saveSubmission(submission: Submission) {
        submissionDao.insertSubmission(submission.toEntity())
    }

    override suspend fun getSubmissionById(id: Int): Submission? {
        return submissionDao.getSubmissionById(id)?.toDomain()
    }

    override suspend fun getAllSubmissions(): List<Submission> {
        return submissionDao.getAllSubmissions().map { it.toDomain() }
    }

    override suspend fun deleteSubmissionById(id: Int) {
        submissionDao.deleteSubmissionById(id)
    }
}
