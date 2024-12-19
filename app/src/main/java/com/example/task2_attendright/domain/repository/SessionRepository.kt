package com.example.task2_attendright.domain.repository

import com.example.task2_attendright.domain.model.Session


interface SessionRepository {
    suspend fun saveSession(session: Session)
    suspend fun getSession(): Session?
    suspend fun clearSession()
}
