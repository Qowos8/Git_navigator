package com.example.git_navigator.di

import com.example.git_navigator.data.network.AuthorizationInterceptor
import com.example.git_navigator.data.network.GitHubService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object RetrofitModule {
    private const val BASE_URL = "https://api.github.com/"
    private const val JSON_MIME_TYPE = "application/json"
    val json = Json{ignoreUnknownKeys = true}

    @Provides
    @Singleton
    fun create (@Named("authToken") authToken: String): GitHubService {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AuthorizationInterceptor(authToken))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(JSON_MIME_TYPE.toMediaType()))
            .client(client)
            .build()
            .create(GitHubService::class.java)
    }
}