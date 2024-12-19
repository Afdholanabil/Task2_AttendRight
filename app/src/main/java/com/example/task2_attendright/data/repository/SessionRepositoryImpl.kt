package com.example.task2_attendright.data.repository

import com.example.task2_attendright.data.local.datastore.SessionDataStore
import com.example.task2_attendright.domain.model.Session
import com.example.task2_attendright.domain.repository.SessionRepository


class SessionRepositoryImpl(
    private val sessionDataStore: SessionDataStore
): SessionRepository {

    override suspend fun saveSession(session: Session) {
        sessionDataStore.saveSession(session)
    }

    override suspend fun getSession(): Session? {
        return sessionDataStore.getSession()
    }

    override suspend fun clearSession() {
        sessionDataStore.clearSession()
    }
}
