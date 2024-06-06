package com.zhixue.lite.core.network.di

import android.content.Context
import coil.ImageLoader
import com.zhixue.lite.core.common.json.NetworkJson
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    @NetworkJson
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesOkHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .build()

    @Provides
    @Singleton
    fun providesImageLoader(
        @ApplicationContext
        context: Context,
        okHttpCallFactory: Lazy<Call.Factory>
    ): ImageLoader = ImageLoader.Builder(context)
        .callFactory { okHttpCallFactory.get() }
        .crossfade(true)
        .respectCacheHeaders(false)
        .build()
}