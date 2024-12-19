package com.example.task2_attendright.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "authority_prefs")

class AuthorityDataStore(private val context: Context) {
    companion object {
        private val PERMISSION_GRANTED = booleanPreferencesKey("permission_granted")
    }

    // Menyimpan status izin
    suspend fun setPermissionGranted(granted: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PERMISSION_GRANTED] = granted
        }
    }

    // Mendapatkan status izin
    fun isPermissionGranted(): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[PERMISSION_GRANTED] ?: false
        }
    }
}
