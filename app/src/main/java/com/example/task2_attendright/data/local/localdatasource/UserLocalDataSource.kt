package com.example.task2_attendright.data.local.localdatasource

import com.example.task2_attendright.data.local.db.user.UserEntity
import com.example.task2_attendright.domain.model.User

interface UserLocalDataSource {
    suspend fun getUserById(userId: String): User?
    suspend fun updateUser(user: User)
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        name = this.name,
        role = this.role,
        gender = this.gender,
        birthDate = this.birthDate,
        phone = this.phone,
        religion = this.religion
    )
}

fun UserEntity.toDomain(): User {
    return User(
        userId = this.userId,
        name = this.name,
        role = this.role,
        gender = this.gender,
        birthDate = this.birthDate,
        phone = this.phone,
        religion = this.religion
    )
}
