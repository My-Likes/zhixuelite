package com.zhixue.lite.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.zhixue.lite.core.common.json.PreferencesJson
import com.zhixue.lite.core.datastore.model.UserPreferences
import com.zhixue.lite.core.datastore.serializer.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {
    @Provides
    @Singleton
    @PreferencesJson
    fun providesPreferencesJson(): Json = Json {
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext
        context: Context,
        userPreferencesSerializer: UserPreferencesSerializer
    ): DataStore<UserPreferences> = DataStoreFactory.create(
        serializer = userPreferencesSerializer
    ) {
        context.dataStoreFile("user_preferences.json")
    }
}