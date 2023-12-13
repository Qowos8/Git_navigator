package com.example.git_navigator.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val inputToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url
        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(PARAMETER_INPUT, inputToken)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .addHeader(HEADER, "$HEADER_VALUE $inputToken")
            .build()

        return chain.proceed(newRequest)
    }
    companion object{
        private const val PARAMETER_INPUT = "inputToken"
        private const val HEADER = "Authorization"
        private const val HEADER_VALUE = "Bearer"
    }
}