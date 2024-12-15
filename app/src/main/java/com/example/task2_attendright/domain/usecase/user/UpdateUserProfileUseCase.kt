package com.example.task2_attendright.domain.usecase.user

import com.example.task2_attendright.domain.model.User
import com.example.task2_attendright.domain.repository.UserRepository

class UpdateUserProfileUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        // Validasi data user, dsb.
        userRepository.updateUserProfile(user)
    }
}