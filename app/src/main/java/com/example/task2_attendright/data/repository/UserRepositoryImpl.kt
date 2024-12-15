package com.example.task2_attendright.data.repository

import com.example.task2_attendright.data.local.localdatasource.UserLocalDataSource
import com.example.task2_attendright.domain.model.User
import com.example.task2_attendright.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun getUserById(userId: String): User? {
        return userLocalDataSource.getUserById(userId)
    }

    override suspend fun updateUserProfile(user: User) {
        userLocalDataSource.updateUser(user)
    }
}