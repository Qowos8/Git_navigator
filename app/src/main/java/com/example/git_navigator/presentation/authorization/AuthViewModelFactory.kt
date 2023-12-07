package com.example.git_navigator.presentation.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.git_navigator.data.network.GitHubService

class AuthViewModelFactory(private val service: GitHubService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            val authApi = service
            return AuthViewModel(service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}