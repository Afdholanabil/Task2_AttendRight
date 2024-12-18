package com.example.task2_attendright.domain.repository

import com.example.task2_attendright.domain.model.User

interface UserRepository {
    suspend fun getUserById(userId: String): User?
    suspend fun updateUserProfile(user: User)
    suspend fun login(email: String, password: String): User?
}
