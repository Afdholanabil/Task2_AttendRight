package com.example.task2_attendright.domain.usecase.session

import com.example.task2_attendright.domain.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckSessionUseCase(
    private val sessionRepository: SessionRepository,
    private val sessionTimeoutMillis: Long
) {
    suspend operator fun invoke(): CheckSessionResult = withContext(Dispatchers.IO) {
        val session = sessionRepository.getSession()
        if (session == null || session.userId.isNullOrEmpty() || !session.rememberMe) {
            return@withContext CheckSessionResult.NoSession
        }

        val currentTime = System.currentTimeMillis()
        return@withContext if ((currentTime - session.sessionStartTime) > sessionTimeoutMillis) {
            CheckSessionResult.SessionExpired
        } else {
            CheckSessionResult.SessionActive(session.userId, session.email!!)
        }
    }
}

sealed class CheckSessionResult {
    object NoSession : CheckSessionResult()
    object SessionExpired : CheckSessionResult()
    data class SessionActive(val userId: String, val email: String) : CheckSessionResult()
}
