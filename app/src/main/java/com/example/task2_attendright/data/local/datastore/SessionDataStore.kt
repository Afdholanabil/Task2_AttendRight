package com.example.task2_attendright.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.task2_attendright.domain.model.Session
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.preferencesDataStore


private val Context.sessionPrefs: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")

class SessionDataStore(private val context: Context) {

    companion object {
        val USER_ID = stringPreferencesKey("session_user_id")
        val EMAIL = stringPreferencesKey("session_email")
        val REMEMBER_ME = booleanPreferencesKey("session_remember_me")
        val SESSION_START = longPreferencesKey("session_start_time")
    }

    suspend fun saveSession(session: Session) {
        context.sessionPrefs.edit { prefs ->
            prefs[USER_ID] = session.userId ?: ""
            prefs[EMAIL] = session.email ?: ""
            prefs[REMEMBER_ME] = session.rememberMe
            prefs[SESSION_START] = session.sessionStartTime
        }
    }

    suspend fun getSession(): Session? {
        val prefs = context.sessionPrefs.data.first()
        val userId = prefs[USER_ID] ?: return null
        val email = prefs[EMAIL] ?: ""
        val rememberMe = prefs[REMEMBER_ME] ?: false
        val startTime = prefs[SESSION_START] ?: 0L
        if (userId.isEmpty()) return null
        return Session(userId, email, rememberMe, startTime)
    }

    suspend fun clearSession() {
        context.sessionPrefs.edit { it.clear() }
    }
}
