package com.example.task2_attendright.domain.usecase.user

import com.example.task2_attendright.domain.model.User
import com.example.task2_attendright.domain.repository.UserRepository


class LoginUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): User? {
        // Bisa tambahkan validasi email dan password di sini
        return userRepository.login(email, password)
    }
}
