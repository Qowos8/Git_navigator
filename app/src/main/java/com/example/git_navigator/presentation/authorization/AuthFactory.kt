package com.example.git_navigator.presentation.authorization

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.git_navigator.data.network.GitHubService
import com.example.git_navigator.data.network.RetrofitBuilder

class AuthFactory(private val service: GitHubService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            val authApi = service
            return AuthViewModel(service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}