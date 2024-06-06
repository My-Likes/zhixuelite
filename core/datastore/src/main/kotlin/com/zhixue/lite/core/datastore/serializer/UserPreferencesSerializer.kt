package com.zhixue.lite.core.datastore.serializer

import androidx.datastore.core.Serializer
import com.zhixue.lite.core.common.json.PreferencesJson
import com.zhixue.lite.core.datastore.model.UserPreference
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

internal class UserPreferencesSerializer @Inject constructor(
    @PreferencesJson
    private val preferencesJson: Json
) : Serializer<List<UserPreference>> {

    override val defaultValue: List<UserPreference> = emptyList()

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun readFrom(input: InputStream): List<UserPreference> {
        return preferencesJson.decodeFromStream(input)
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun writeTo(t: List<UserPreference>, output: OutputStream) {
        preferencesJson.encodeToStream(t, output)
    }
}