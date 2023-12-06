package com.example.git_navigator.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val AUTH_URL = "https://github.com/login/oauth/authorize"
    private const val BASE_URL = "https://api.github.com/"
    private const val clientId = "ae6e07869dd3e0de5559"
    private const val clientSecret = "d488d7ea8158e55aa9a4940c2fcaeb7d7b6ea88a"
    private const val CALLBACK_URL = "ru.kts.oauth://github.com/callback"
    private const val AUTH_TOKEN = "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"
    fun create(usertoken: String): GitHubService {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AuthInterceptor(usertoken))
            .build()
        val clientik = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientik)
            .build()

        return retrofit.create(GitHubService::class.java)
    }



}