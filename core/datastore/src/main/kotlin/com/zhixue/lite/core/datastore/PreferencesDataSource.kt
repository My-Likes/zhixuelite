package com.zhixue.lite.core.datastore

import androidx.datastore.core.DataStore
import com.zhixue.lite.core.datastore.model.UserPreference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val userPreferencesDataStore: DataStore<List<UserPreference>>
) {
    val userPreferences: Flow<List<UserPreference>> = userPreferencesDataStore.data

    suspend fun setUserPreference(userPreference: UserPreference) {
        userPreferencesDataStore.updateData { preferences ->
            (listOf(userPreference) + preferences).distinctBy { it.id }
        }
    }
}