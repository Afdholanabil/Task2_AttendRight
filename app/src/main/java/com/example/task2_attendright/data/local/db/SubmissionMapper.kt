package com.example.task2_attendright.data.local.db

import com.example.task2_attendright.data.local.db.submission.SubmissionEntity
import com.example.task2_attendright.domain.enums.SubmissionStatus
import com.example.task2_attendright.domain.enums.SubmissionType
import com.example.task2_attendright.domain.model.Submission


fun Submission.toEntity(): SubmissionEntity {
    return SubmissionEntity(
        submissionId = this.submissionId,
        type = this.type.name,
        title = this.title,
        startDate = this.startDate,
        endDate = this.endDate,
        time = this.time,
        description = this.description,
        imageUri = this.imageUri,
        status = this.status.name
    )
}

fun SubmissionEntity.toDomain(): Submission {
    return Submission(
        submissionId = this.submissionId,
        type = SubmissionType.valueOf(this.type),
        title = this.title,
        startDate = this.startDate,
        endDate = this.endDate,
        time = this.time,
        description = this.description,
        imageUri = this.imageUri,
        status = SubmissionStatus.valueOf(this.status)
    )
}
