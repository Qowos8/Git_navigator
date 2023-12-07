package com.example.git_navigator.data.network

import okhttp3.Interceptor
import okhttp3.Response

class RepInterceptor(private val inputToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url
        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("inputToken", inputToken)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .addHeader("Authorization", "Bearer $inputToken")
            .build()

        return chain.proceed(newRequest)
    }
}