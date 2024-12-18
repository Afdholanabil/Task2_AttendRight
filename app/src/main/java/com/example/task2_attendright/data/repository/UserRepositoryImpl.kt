package com.example.task2_attendright.data.repository

import com.example.task2_attendright.data.local.db.toDomain
import com.example.task2_attendright.data.local.db.toEntity
import com.example.task2_attendright.data.local.db.user.UserDao
import com.example.task2_attendright.domain.model.User
import com.example.task2_attendright.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUserById(userId: String): User? {
        val entity = userDao.getUserById(userId)
        return entity?.toDomain()
    }

    override suspend fun updateUserProfile(user: User) {
        userDao.updateUser(user.toEntity())
    }

    override suspend fun login(email: String, password: String): User? {
        val entity = userDao.getUserByEmailPassword(email, password)
        return entity?.toDomain()
    }
}
