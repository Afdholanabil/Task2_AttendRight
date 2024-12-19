package com.example.task2_attendright.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

private val Context.dataStore by preferencesDataStore(name = "attendance_prefs")

class AttendanceDataStore(private val context: Context) {
    companion object {
        private val HAS_CLOCKED_IN = booleanPreferencesKey("has_clocked_in")
        private val HAS_CLOCKED_OUT = booleanPreferencesKey("has_clocked_out")
        private val LAST_CHECKED_DATE = stringPreferencesKey("last_checked_date")
    }

    suspend fun setClockIn() {
        context.dataStore.edit { prefs ->
            prefs[HAS_CLOCKED_IN] = true
            prefs[HAS_CLOCKED_OUT] = false
            prefs[LAST_CHECKED_DATE] = getCurrentDate()
        }
    }

    suspend fun setClockOut() {
        context.dataStore.edit { prefs ->
            prefs[HAS_CLOCKED_OUT] = true
        }
    }

    fun getClockStatus(): Flow<ClockStatus> {
        return context.dataStore.data.map { prefs ->
            val currentDate = getCurrentDate()
            val lastCheckedDate = prefs[LAST_CHECKED_DATE] ?: ""

            if (currentDate != lastCheckedDate) {
                ClockStatus(false, false)
            } else {
                ClockStatus(
                    hasClockedIn = prefs[HAS_CLOCKED_IN] ?: false,
                    hasClockedOut = prefs[HAS_CLOCKED_OUT] ?: false
                )
            }
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }
}

data class ClockStatus(val hasClockedIn: Boolean, val hasClockedOut: Boolean)
