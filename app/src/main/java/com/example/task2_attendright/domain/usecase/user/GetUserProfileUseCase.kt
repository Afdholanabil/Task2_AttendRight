package com.example.task2_attendright.domain.usecase.user

import com.example.task2_attendright.domain.model.User
import com.example.task2_attendright.domain.repository.UserRepository

class GetUserProfileUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): User? {
        return userRepository.getUserById(userId)
    }
}