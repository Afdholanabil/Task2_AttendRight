package com.example.task2_attendright.data.local.db

import com.example.task2_attendright.data.local.db.user.UserEntity
import com.example.task2_attendright.domain.model.User

fun User.toEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        name = this.name,
        role = this.role,
        gender = this.gender,
        birthDate = this.birthDate,
        phone = this.phone,
        religion = this.religion,
        email = this.email,
        password = this.password
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
        religion = this.religion,
        email = this.email,
        password = this.password
    )
}
