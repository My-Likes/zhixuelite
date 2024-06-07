package com.zhixue.lite.core.datastore

import androidx.datastore.core.DataStore
import com.zhixue.lite.core.datastore.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val userPreferencesDataStore: DataStore<UserPreferences>
) {
    val userPreferences: Flow<UserPreferences> = userPreferencesDataStore.data

    suspend fun setUserInfo(
        id: String, avatar: String,
        name: String, className: String, schoolName: String,
        grantTicket: String
    ) {
        userPreferencesDataStore.updateData {
            it.copy(
                id = id,
                avatar = avatar,
                name = name,
                className = className,
                schoolName = schoolName,
                grantTicket = grantTicket
            )
        }
    }

    suspend fun clearUserInfo() {
        userPreferencesDataStore.updateData {
            it.copy(
                id = "",
                avatar = "",
                name = "",
                className = "",
                schoolName = "",
                grantTicket = ""
            )
        }
    }
}