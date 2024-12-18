package com.example.task2_attendright.data.local.localdatasource


import com.example.task2_attendright.data.local.localdatasource.UserLocalDataSource

import com.example.task2_attendright.data.local.db.user.UserDao
import com.example.task2_attendright.domain.model.User


class UserLocalDataSourceImpl(
    private val userDao: UserDao
) : UserLocalDataSource {

    override suspend fun getUserById(userId: String): User? {
        val entity = userDao.getUserById(userId)
        return entity?.toDomain()
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user.toEntity())
    }

}
