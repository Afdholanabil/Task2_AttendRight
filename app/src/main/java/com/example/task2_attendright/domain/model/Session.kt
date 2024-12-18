package com.example.task2_attendright.domain.model

data class Session(
    val userId: String?,
    val email: String?,
    val rememberMe: Boolean,
    val sessionStartTime: Long // waktu login dalam millis
)
