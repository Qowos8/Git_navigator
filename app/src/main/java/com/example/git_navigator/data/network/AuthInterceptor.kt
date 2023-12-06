package com.example.git_navigator.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

open class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        //val originalHttpUrl = originalRequest.url
        /*val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", token)
            .build()*/
        val newRequest = originalRequest.newBuilder()
            //.url(newUrl)
            .addHeader("Authorization", "Bearer $token")
            .build()
        val response = chain.proceed(newRequest)

        Log.d("AuthInterceptor", "Response Code: ${response.code}")
        Log.d("AuthInterceptor", "Response Message: ${response.message}")

        return response
    }
}