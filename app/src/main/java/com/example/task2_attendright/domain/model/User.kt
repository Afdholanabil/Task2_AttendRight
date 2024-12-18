package com.example.task2_attendright.domain.model

data class User(
    val userId: String,
    val name: String,
    val role: String,
    val gender: String,
    val birthDate: String,
    val phone: String,
    val religion: String,
    val email: String,          // Tambahkan email
    val password: String        // Tambahkan password untuk login
)
