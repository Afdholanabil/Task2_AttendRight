package com.example.task2_attendright.domain.model

data class User(
    val userId: String,
    val name: String,
    val role: String,     // ex: "UI/UX Designer"
    val gender: String,   // "Male" / "Female"
    val birthDate: String,
    val phone: String,
    val religion: String  // "Islam", "Kristen", dsb.
)