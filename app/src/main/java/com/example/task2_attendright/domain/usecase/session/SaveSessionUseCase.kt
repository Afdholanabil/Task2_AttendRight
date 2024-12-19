package com.example.task2_attendright.domain.usecase.session

import com.example.task2_attendright.domain.model.Session
import com.example.task2_attendright.domain.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveSessionUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(userId: String, email: String, rememberMe: Boolean) = withContext(Dispatchers.IO) {
        val session = Session(
            userId = userId,
            email = email,
            rememberMe = rememberMe,
            sessionStartTime = System.currentTimeMillis()
        )
        sessionRepository.saveSession(session)
    }
}


