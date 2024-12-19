package com.example.task2_attendright.domain.model

import com.example.task2_attendright.domain.enums.SubmissionStatus
import com.example.task2_attendright.domain.enums.SubmissionType

data class Submission(
    val submissionId: Int = 0,
    val type: SubmissionType,
    val title: String?,
    val startDate: String?,
    val endDate: String?,
    val time: String?,
    val description: String?,
    val imageUri: String?,
    val status: SubmissionStatus
)