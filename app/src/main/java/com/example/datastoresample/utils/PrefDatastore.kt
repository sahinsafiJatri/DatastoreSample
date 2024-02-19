package com.example.datastoresample.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val USER_PREFERENCES_NAME = "user_preferences"
private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

class PrefDatastore(private val context: Context) {

    private val dispatcher = Dispatchers.IO

    fun updateString(key : String, value : String) {
        CoroutineScope(dispatcher).launch {
            context.dataStore.edit { mutablePreferences ->
                mutablePreferences[stringPreferencesKey(key)] = value
            }
        }
    }

    fun updateInt(key : String, value : Int) {
        CoroutineScope(dispatcher).launch {
            context.dataStore.edit { mutablePreferences ->
                mutablePreferences[intPreferencesKey(key)] = value
            }
        }
    }

    fun updateBoolean(key : String, value : Boolean) {
        CoroutineScope(dispatcher).launch {
            context.dataStore.edit { mutablePreferences ->
                mutablePreferences[booleanPreferencesKey(key)] = value
            }
        }
    }


    fun observeString(key: String) : Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: ""
        }
    }

    fun observeInt(key: String) : Flow<Int> {
        return context.dataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)] ?: 0
        }
    }

    fun observeBoolean(key: String) : Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: false
        }
    }

}