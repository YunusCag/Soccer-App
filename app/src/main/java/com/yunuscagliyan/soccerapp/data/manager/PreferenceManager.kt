package com.yunuscagliyan.soccerapp.data.manager

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


data class ScreenModePreference(val isNightMode: Boolean)

@Singleton
class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.createDataStore("user_preferences")
    val preferencesFlow = dataStore.data
        .catch { exception ->
            Timber.e("Error reading preferences.Message:${exception.message}")
            emit(emptyPreferences())
        }
        .map { preferences ->
            val isNightMode = preferences[PreferencesKeys.IS_NIGHT_MODE] ?: false
            ScreenModePreference(isNightMode)
        }

    suspend fun updateIsNightMode(nightMode:Boolean){
        dataStore.edit { preferences->
            preferences[PreferencesKeys.IS_NIGHT_MODE]=nightMode
        }
    }

    private object PreferencesKeys {
        val IS_NIGHT_MODE = booleanPreferencesKey("is_night_mode")
    }
}